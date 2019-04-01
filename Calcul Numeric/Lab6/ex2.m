% hăumuărc
clear all;

Time = 1:7;
Temperature = [13, 15, 20, 14, 15, 13 10];
MAX_DEGREE = 5;
Graph = 1:0.05:8;

plot(Time, Temperature, 'x', 'MarkerSize', 10); hold on;

for i = 1:MAX_DEGREE
	PF = polyfit(Time, Temperature, i);
	G = polyval(PF, Graph);	
	plot(8, G(end), 'o', 'MarkerSize', 10); hold on;
	plot(Graph, G); hold on;
end
