import copy

from State import State


class Sudoku:
    def __init__(self, sqrt, file_name):
        self.initial_state = State(sqrt, file_name)

    def __str__(self):
        return str(self.initial_state)

    def expand(self):
        next_states = []
        for i in range(self.initial_state.size):
            for j in range(self.initial_state.size):
                if self.initial_state.matrix[i][j] == 0:
                    for k in range(1, self.initial_state.size + 1):
                        if self.initial_state.placeable(i, j, k):
                            next_state = copy.deepcopy(self)
                            next_state.set_value(i, j, k)
                            next_states.append(next_state)
        return next_states  # list of States

    def expand_best(self):
        coefficient = [[0 for _x in range(self.initial_state.size)] for _y in range(self.initial_state.size)]
        for i in range(self.initial_state.size):
            for j in range(self.initial_state.size):
                if self.initial_state.matrix[i][j] == 0:
                    for k in range(1, self.initial_state.size + 1):
                        if self.initial_state.placeable(i, j, k):
                            coefficient[i][j] += 1
        best_result = 9
        for i in range(self.initial_state.size):
            for j in range(self.initial_state.size):
                if self.initial_state.matrix[i][j] == 0 and coefficient[i][j] < best_result:
                    best_result = coefficient[i][j]
        next_states = []
        for i in range(self.initial_state.size):
            for j in range(self.initial_state.size):
                if self.initial_state.matrix[i][j] == 0 and coefficient[i][j] == best_result:
                    for k in range(1, self.initial_state.size + 1):
                        if self.initial_state.placeable(i, j, k):
                            next_state = copy.deepcopy(self)
                            next_state.set_value(i, j, k)
                            next_states.append(next_state)
        return next_states

    def heuristic(self):
        pass

    def completed(self):
        for i in self.initial_state.matrix:
            for j in i:
                if j == 0:
                    return False
        return True

    def get_value(self, i, j):
        return self.initial_state.matrix[i][j]

    def set_value(self, i, j, k):
        self.initial_state.matrix[i][j] = k

    def place_solution(self):
        # puts solutions where it is certain
        coefficient = [[0 for _x in range(self.initial_state.size)] for _y in range(self.initial_state.size)]
        for i in range(self.initial_state.size):
            for j in range(self.initial_state.size):
                if self.initial_state.matrix[i][j] == 0:
                    for k in range(1, self.initial_state.size + 1):
                        if self.initial_state.placeable(i, j, k):
                            coefficient[i][j] += 1
        for i in range(self.initial_state.size):
            for j in range(self.initial_state.size):
                if self.initial_state.matrix[i][j] == 0 and coefficient[i][j] == 1:
                    for k in range(1, self.initial_state.size + 1):
                        if self.initial_state.placeable(i, j, k):
                            self.initial_state.matrix[i][j] = k

    def diff(self, state):
        string_builder = ""
        for i in range(self.initial_state.size):
            for j in range(self.initial_state.size):
                if self.get_value(i, j) != state.get_value(i, j):
                    string_builder += "at %d, %d %d != %d" % (i, j, self.get_value(i, j), state.get_value(i, j))
        return string_builder

    def __eq__(self, other):
        for i in range(self.initial_state.size):
            for j in range(self.initial_state.size):
                if self.initial_state.matrix[i][j] != other.get_value(i, j):
                    return False
        return True

    def unsolved_cell(self):
        cnt = 0
        for i in range(self.initial_state.size):
            for j in range(self.initial_state.size):
                if self.initial_state.matrix[i][j] == 0:
                    cnt += 1
        return cnt
