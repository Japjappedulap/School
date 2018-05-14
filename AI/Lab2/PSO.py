import numpy


class Ant:
    dx = [-2, -2, -1, -1, 1, 1, 2, 2]
    dy = [-1, 1, -2, 2, -2, 2, -1, 1]

    def __init__(self, size_x, size_y):
        self.size_x = size_x
        self.size_y = size_y
        self.size = size_x * size_y
        self.conf = [numpy.random.randint(low=0, high=self.size)]

    def possible_moves(self, index=None):
        next_possible_moves = []
        if index is None:
            index = self.conf[-1]
        x = index // self.size_y
        y = index % self.size_y
        for direction in range(8):
            next_x = x + self.dx[direction]
            next_y = y + self.dy[direction]
            if 0 <= next_x < self.size_x and 0 <= next_y < self.size_y:
                # valid move
                next_index = next_x * self.size_y + next_y
                if next_index not in self.conf:
                    next_possible_moves.append(next_index)
        return next_possible_moves.copy()

    def move_coefficient(self, index):
        # dummy = Ant(self.size_x, self.size_y)
        # dummy.conf = self.conf.copy()
        # dummy.conf.append(index)
        # return 9 - len(dummy.possible_moves(index))
        # return 8 - len(self.possible_moves(index))
        return 1

    def ok_move(self, pos1, pos2):
        pos1_x = pos1 // self.size_y
        pos1_y = pos1 % self.size_y
        pos2_x = pos2 // self.size_y
        pos2_y = pos2 % self.size_y
        for dire in range(8):
            if self.dx[dire] + pos1_x == pos2_x and self.dy[dire] + pos1_y == pos2_y:
                return True
        return False

    def update(self, q0, trace, alpha, beta):
        next_moves = self.possible_moves().copy()
        if len(next_moves) == 0:
            return False
        p = [0 for _ in range(self.size)]
        for i in next_moves:
            p[i] = self.move_coefficient(i)
        for i in range(len(p)):
            p[i] = trace[self.conf[-1]][i] ** alpha + p[i] ** beta
        if numpy.random.uniform(0, 1.0) < q0:
            index_to_add = -1
            max_value = 0
            for i in range(len(p)):
                if max_value < p[i] and i not in self.conf:
                    index_to_add = i
                    max_value = p[i]
            self.conf.append(index_to_add)
        else:
            for i in range(len(p)):
                if not self.ok_move(self.conf[-1], i):
                    p[i] = 0
                if i in self.conf:
                    p[i] = 0
            total = sum(p)
            pdf = [p[i] / total for i in range(len(p))]
            index = [i for i in range(len(p))]
            index_to_add = numpy.random.choice(index, 1, p=pdf).tolist()[0]
            self.conf.append(index_to_add)
        # print(self.conf)
        return True

    def fitness(self):
        return len(self.conf)


# def print_to_map(table, n, m):
#     k = 0
#     for _ in range(n):
#         for _ in range(m):
#             print(table[k], end=", ")
#             k += 1
#         print()


class Controller:
    def __init__(self, size_x, size_y,
                 epoch_no, ant_no, alpha,
                 beta, rho, q0):
        self.size_x = size_x
        self.size_y = size_y
        self.epoch_no = epoch_no
        self.ant_no = ant_no
        self.alpha = alpha
        self.beta = beta
        self.rho = rho
        self.q0 = q0
        self.trace = [[1 for _ in range(size_x * size_y)] for _ in range(size_x * size_y)]
        # for i in self.trace:
        #     print(i)

    def solve(self):
        best_sol = []
        for i in range(self.epoch_no):
            sol = self.run_epoch.copy()
            if len(sol) > len(best_sol):
                best_sol = sol.copy()
        return best_sol

    @property
    def run_epoch(self):
        ants = [Ant(self.size_x, self.size_y) for _ in range(self.ant_no)]

        for i in range(self.size_x * self.size_y):
            for x in ants:
                x.update(self.q0, self.trace, self.alpha, self.beta)
        d_trace = [1.0 / ants[i].fitness() for i in range(len(ants))]

        for i in range(self.size_x * self.size_y):
            for j in range(self.size_x * self.size_y):
                self.trace[i][j] = (1 - self.rho) * self.trace[i][j]

        for i in range(len(ants)):
            for j in range(len(ants[i].conf) - 1):
                x = ants[i].conf[j]
                y = ants[i].conf[j + 1]
                self.trace[x][y] = self.trace[x][y] + d_trace[i]

        f = [[ants[i].fitness(), i] for i in range(len(ants))]
        f = max(f)
        return ants[f[1]].conf


class Problem:
    def __init__(self, size_x, size_y,
                 epoch_no, ant_no, alpha,
                 beta, rho, q0):
        self.size_x = size_x
        self.size_y = size_y
        self.controller = Controller(size_x, size_y, epoch_no, ant_no, alpha, beta, rho, q0)

    def solve(self):
        print("Attempting solve, this may take a while")
        solution = self.controller.solve()
        print("Solution's length: %d, out of %d, percentage: %.2f" % (len(solution), self.size_x * self.size_y,
                                                                      100 * (len(solution) / (
                                                                                  self.size_x * self.size_y))))
        print("Path found:", solution)
        fancy_print(solution, self.size_x, self.size_y)
        okay(solution)


def fancy_print(conf, n, m):
    path = [-1 for _ in range(n * m)]
    k = 1
    for i in conf:
        path[i] = k
        k += 1
    k = 0
    for i in range(n):
        for j in range(m):
            print(path[k], end=", ")
            k += 1
        print()


def okay(path):
    path_copy = path.copy()
    for i in range(len(path_copy)):
        val = path_copy[i]
        path_copy[i] = 0
        if val in path_copy and val != 0:
            print("DUBLURA - ai gresit verificarile, baiatul meu")


if __name__ == '__main__':
    prob = Problem(8, 8, 500, 3, 1.9, 0.9, 0.05, 0.5)
    prob.solve()
