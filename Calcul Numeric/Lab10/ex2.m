clear('all');

A = [6 2 1 -1; 2 4 1 0; 1 1 4 -1; -1 0 -1 3];
b = [8; 7; 5; 1];

%% forwardSubstitution: solves a lower triangular system using forward substitution
function x = forwardSubstitution(A, b)
	n = length(b);
	x = NaN(size(b));
	for i = 1 : n 
		x(i) = (1 / A(i, i)) * (b(i) - A(i, 1:i-1) * x(1:i-1));
	end
end

%% backwardSubstitution: solves a upper triangular system using backward substitution
function x = backwardSubstitution(A, b)
	n = length(b);
	x = NaN(size(b));
	for i = n : -1 : 1 
		x(i) = (1 / A(i, i)) * (b(i) - A(i, i+1:n) * x(i+1:n));
	end
end

%% doolittle: returns the LU decomposition of a linear system
function [L, U] = doolittle(A)
	[n, ~] = size(A);
	I = eye(n); L = I;
	for k = 1 : n-1
		t = zeros(n, 1);
		t(k + 1 : n) = A(k + 1 : n, k) / A(k, k);
		l = zeros(1, n); l(k) = 1;
		A = (I - t * l) * A;
		L = L * (I + t * l);
	end
	U = A;
end

%% LUsolve: solves a linear system using LU decomposition
function [x] = LUsolve(A, b)
	[L, U] = lu(A); 
	% [L, U] = doolittle(A); % can use this line also
	y = forwardSubstitution(L, b);
	x = backwardSubstitution(U, y);
end

disp(LUsolve(A, b));
