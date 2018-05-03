import numpy


class Algorithm:
    def __init__(self, starting_population, individual_x, individual_y, mutation_probability, iterations):
        self.population = Population(starting_population, individual_x, individual_y, starting_population)
        self.iterations = iterations
        self.individual_x = individual_x
        self.individual_y = individual_y
        self.individuals_for_selection = starting_population
        self.mutation_probability = mutation_probability

    def run(self):
        previous_evaluation = 0
        for i in range(self.iterations):
            current_evaluation = self.population.evaluate()
            if previous_evaluation != current_evaluation:
                previous_evaluation = current_evaluation
                print(current_evaluation)
            self.iteration()
            self.population.trim()
        print(self.population.evaluate())
        self.population.best().dbg_fancy()

    def iteration(self):
        selection = self.population.selection(len(self.population.pool))
        parent1 = numpy.random.permutation(len(self.population.pool))
        parent2 = numpy.random.permutation(len(self.population.pool))
        children = []
        for i in range(len(parent1)):

            child = selection[parent1[i]] + selection[parent2[i]]
            if numpy.random.uniform(0, 1) < self.mutation_probability:
                child.mutate()
            children.append(selection[parent1[i]] + selection[parent2[i]])
            self.population.add(Individual(self.individual_x, self.individual_y))


class Population:
    def __init__(self, size, individual_x, individual_y, max_size):
        self.pool = []
        self.max_size = max_size
        for i in range(size):
            self.pool.append(Individual(individual_x, individual_y))

    def selection(self, size):
        pdf = []
        for i in self.pool:
            pdf.append(i.fitness() ** 2)
        total = sum(pdf)
        for i in range(len(pdf)):
            pdf[i] /= total
        result = numpy.random.choice(self.pool, size, p=pdf, replace=False).tolist()
        return result

    def evaluate(self):
        result = 0
        for i in self.pool:
            if i.fitness() > result:
                result = i.fitness()
        return result

    def best(self):
        best_coefficient = self.evaluate()
        for i in self.pool:
            if i.fitness() == best_coefficient:
                return i
        return None

    def trim(self):
        if len(self.pool) <= self.max_size:
            return
        pdf = []
        for i in self.pool:
            pdf.append(i.fitness() ** 2)
        total = sum(pdf)
        for i in range(len(pdf)):
            pdf[i] /= total
        self.pool = numpy.random.choice(self.pool, self.max_size, p=pdf, replace=False).tolist()

    def add(self, individual):
        self.pool.append(individual)


class Individual:
    dx = [-2, -2, -1, -1, 1, 1, 2, 2]
    dy = [-1, 1, -2, 2, -2, 2, -1, 1]

    def __init__(self, x, y):
        # table size
        self.x = x
        self.y = y
        self.conf = []
        for i in range(x * y):
            self.conf.append(0)
        self.size = self.x * self.y
        # valid moves
        self.initialise()

    def initialise(self):
        self.conf = numpy.random.permutation(self.size).tolist()

    def __str__(self):
        return str(self.conf)

    def dbg(self):
        for i in range(self.x):
            for j in range(self.y):
                print(self.conf[i * self.y + j], end=" ")
            print()

    def dbg_fancy(self):
        to_print = self.fancy_map()
        for i in range(self.x):
            for j in range(self.y):
                print(to_print[i * self.y + j], end=" ")
            print()

    def ok_move(self, x, y):
        i1 = x // self.y
        j1 = x % self.y
        i2 = y // self.y
        j2 = y % self.y
        for direction in range(len(self.dx)):
            if i1 + self.dx[direction] == i2 and j1 + self.dy[direction] == j2:
                return True
        return False

    def fitness(self):
        fitness_value = 1
        for i in range(1, self.size):
            if self.ok_move(self.conf[i - 1], self.conf[i]):
                fitness_value += 1
            else:
                break
        return fitness_value

    def fancy_map(self):
        new_list = []
        for _ in range(self.size):
            new_list.append(-1)
        k = 0
        for i in self.conf:
            new_list[i] = k
            k += 1
        return new_list

    def mutate(self):
        i, j = 0, 0
        while i == j:
            i, j = numpy.random.random_integers(0, self.size - 1), numpy.random.random_integers(0, self.size - 1)
        aux = self.conf[i]
        self.conf[i] = self.conf[j]
        self.conf[j] = aux

    def crossover(self, self2):
        offspring = Individual(self.x, self.y)
        i = numpy.random.random_integers(0, self.size - 2)
        j = numpy.random.random_integers(i, self.size - 1)
        # i < j !
        for k in range(self.size):
            offspring.conf[k] = -1
        for k in range(i, j + 1):
            offspring.conf[k] = self.conf[k]
        k1 = j + 1
        k2 = j + 1
        while -1 in offspring.conf:
            if k1 == offspring.size:
                k1 = 0
            if k2 == self2.size:
                k2 = 0
            if not self2.conf[k2] in offspring.conf:
                offspring.conf[k1] = self2.conf[k2]
                k1 += 1
            k2 += 1
        return offspring

    def __add__(self, other):
        if not isinstance(other, Individual):
            return None
        return self.crossover(other)

    def __lt__(self, other):
        if self.fitness() < other.fitness():
            return True
        return False


def main():
    problem = Algorithm(100, 8, 8, 0.01, 10000)
    problem.run()


if __name__ == '__main__':
    main()
