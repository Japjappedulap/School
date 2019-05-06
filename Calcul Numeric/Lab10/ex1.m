clear('all');

A = [ 1 1 1 1; 2 3 1 5; -1 1 -5 3; 3 1 7 -2];
b = [10; 31; -2; 18];

%% backwardSubstitution: function description
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

disp(gaussElimination(A, b));
