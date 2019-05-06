clear('all');

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

%% gaussElimination: gauss elimination algorithm
function x = gaussElimination(A, b)
	n = length(b);
	for p = 1 : n-1
		[~, q] = max(abs(A(p:n, p)));
		q = q + p - 1;
		A([p, q], :) = A([q, p], :);
		b([p, q]) = b([q, p]);
		for i = p + 1 : n
			m = A(i, p) / A(p, p);
			A(i, :) = A(i, :) - m * A(p, :);
			b(i) = b(i) - m * b(p);
		end
	end
	x = backwardSubstitution(A, b);
end

%% LUsolve: solves a linear system using LU decomposition
function [x] = LUsolve(A, b)
	[L, U] = lu(A);
	y = forwardSubstitution(L, b);
	x = backwardSubstitution(U, y);
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
