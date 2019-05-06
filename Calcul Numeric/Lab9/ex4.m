clear('all');

% a)
for i = 10 : 15
    x = 1 ./ (1 : i);
    cond(vander(x))
end

disp(''); % just to split

% b)
for i = 10 : 15
    x = ((1 : i) .* 2) - 1;
    cond(vander(x))
end
