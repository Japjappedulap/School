import copy

import datetime


class State:
    def __init__(self, size=2, loading_file=None):
        self.__size = size ** 2
        self.__root = size
        self.__matrix = [[0 for _x in range(self.__size)] for _y in range(self.__size)]
        if loading_file is not None:
            self.load(loading_file)

    def __str__(self):
        string_builder = ""
        k1 = 0
        for i in self.__matrix:
            k2 = 0
            for j in i:
                string_builder += str(j)
                string_builder += ' '
                if (k2 + 1) % self.__root == 0 and (k2 + 1) != self.__size:
                    string_builder += "| "
                k2 += 1
            string_builder += '\n'
            if (k1 + 1) % self.__root == 0 and (k1 + 1) != self.__size:
                string_builder += ("-" * (2 * self.__size + 2 * self.__root - 3))
                string_builder += '\n'
            k1 += 1
        return string_builder

    def load(self, file_name):
        with open(file_name, "r") as file:
            k = 0
            for line in file:
                self.__matrix[k] = line.split()
                k += 1
        for i in range(self.__size):
            for j in range(self.__size):
                self.__matrix[i][j] = int(self.__matrix[i][j])

    def get_value(self, i, j):
        return self.__matrix[i][j]

    def set_value(self, i, j, k):
        self.__matrix[i][j] = k

    def completed(self):
        for i in self.__matrix:
            for j in i:
                if j == 0:
                    return False
        return True

    def get_next_states(self):
        next_states = []
        for i in range(self.__size):
            for j in range(self.__size):
                if self.__matrix[i][j] == 0:
                    for k in range(1, self.__size + 1):
                        if self.placeable(i, j, k):
                            next_state = copy.deepcopy(self)
                            next_state.set_value(i, j, k)
                            next_states.append(next_state)
        return next_states

    def get_next_best_states(self):
        coefficient = [[0 for _x in range(self.__size)] for _y in range(self.__size)]
        for i in range(self.__size):
            for j in range(self.__size):
                if self.__matrix[i][j] == 0:
                    for k in range(1, self.__size + 1):
                        if self.placeable(i, j, k):
                            coefficient[i][j] += 1
        best_result = 9
        for i in range(self.__size):
            for j in range(self.__size):
                if self.__matrix[i][j] == 0 and coefficient[i][j] < best_result:
                    best_result = coefficient[i][j]
        next_states = []
        for i in range(self.__size):
            for j in range(self.__size):
                if self.__matrix[i][j] == 0 and coefficient[i][j] == best_result:
                    for k in range(1, self.__size + 1):
                        if self.placeable(i, j, k):
                            next_state = copy.deepcopy(self)
                            next_state.set_value(i, j, k)
                            next_states.append(next_state)
        return next_states

    def place_solution(self):
        # puts solutions where it is certain
        coefficient = [[0 for _x in range(self.__size)] for _y in range(self.__size)]
        for i in range(self.__size):
            for j in range(self.__size):
                if self.__matrix[i][j] == 0:
                    for k in range(1, self.__size + 1):
                        if self.placeable(i, j, k):
                            coefficient[i][j] += 1
        for i in range(self.__size):
            for j in range(self.__size):
                if self.__matrix[i][j] == 0 and coefficient[i][j] == 1:
                    for k in range(1, self.__size + 1):
                        if self.placeable(i, j, k):
                            self.__matrix[i][j] = k

    def placeable(self, i, j, k):
        """
        :param i:
        :param j:
        :param k:
        :return:
        """
        i_min = (i // self.__root) * self.__root
        j_min = (j // self.__root) * self.__root
        i_max = i_min + self.__root
        j_max = j_min + self.__root
        for i1 in range(i_min, i_max):
            for j1 in range(j_min, j_max):
                # print(i1, j1)
                if k == self.__matrix[i1][j1]:
                    return False
        for i1 in range(self.__size):
            # print(i1)
            if k == self.__matrix[i1][j]:
                return False
        for j1 in range(self.__size):
            # print(j1)
            if k == self.__matrix[i][j1]:
                return False
        return True

    def diff(self, state):
        string_builder = ""
        for i in range(self.__size):
            for j in range(self.__size):
                if self.get_value(i, j) != state.get_value(i, j):
                    string_builder += "at %d, %d %d != %d" % (i, j, self.get_value(i, j), state.get_value(i, j))
        return string_builder

    def __eq__(self, other):
        for i in range(self.__size):
            for j in range(self.__size):
                if self.__matrix[i][j] != other.get_value(i, j):
                    return False
        return True

    def equals(self, state):
        for i in range(self.__size):
            for j in range(self.__size):
                if self.__matrix[i][j] != state.get_value(i, j):
                    return False
        return True

    def unsolved_cell(self):
        cnt = 0
        for i in range(self.__size):
            for j in range(self.__size):
                if self.__matrix[i][j] == 0:
                    cnt += 1
        return cnt


def bfs(initial_state):
    queue = [initial_state]
    while len(queue) != 0:
        # pick first element from queue
        current_state = queue.pop(0)
        print(len(queue))
        if current_state.completed():
            return current_state
        next_states = current_state.get_next_states()

        # check if some of the next states are already in queue
        # if there are, we do not add them
        for next_state in next_states:
            to_add = True
            for existing_state in queue:
                if next_state == existing_state:
                    to_add = False
                    break
            if to_add:
                queue.append(next_state)


def gbfs(initial_state):
    queue = [initial_state]
    previous_len = 0
    while len(queue) != 0:
        # pick first element from queue
        current_state = queue.pop(0)
        print("%6d    diff: %6d" % (len(queue), len(queue) - previous_len))
        previous_len = len(queue)
        if current_state.completed():
            return current_state
        next_states = current_state.get_next_best_states()

        # check if some of the next states are already in queue
        # if there are, we do not add them
        for next_state in next_states:
            to_add = True
            for existing_state in queue:
                if next_state == existing_state:
                    to_add = False
                    break
            if to_add:
                queue.append(next_state)


def tuned_gbfs(initial_state):
    queue = [initial_state]
    previous_len = 0
    while len(queue) != 0:
        # pick first element from queue
        current_state = queue.pop(0)
        current_state.place_solution()

        print("%6d    diff: %6d, unsolved cells: %2d" % (len(queue), len(queue) - previous_len,
                                                         current_state.unsolved_cell()))
        previous_len = len(queue)

        if current_state.completed():
            return current_state
        next_states = current_state.get_next_best_states()

        # check if some of the next states are already in queue
        # if there are, we do not add them
        for next_state in next_states:
            to_add = True
            for existing_state in queue:
                if next_state == existing_state:
                    to_add = False
                    break
            if to_add:
                queue.append(next_state)


if __name__ == '__main__':
    start_time = datetime.datetime.now()
    x = State(3, "in3.txt")
    print(x)
    final_state = tuned_gbfs(x)
    print(final_state)
    finish_time = datetime.datetime.now()

    print(finish_time - start_time)
