import copy


class State:
    def __init__(self, size=2, loading_file=None):
        self.size = size ** 2
        self.root = size
        self.matrix = [[0 for _x in range(self.size)] for _y in range(self.size)]
        if loading_file is not None:
            self.load(loading_file)

    def __str__(self):
        string_builder = ""
        k1 = 0
        for i in self.matrix:
            k2 = 0
            for j in i:
                string_builder += str(j)
                string_builder += ' '
                if (k2 + 1) % self.root == 0 and (k2 + 1) != self.size:
                    string_builder += "| "
                k2 += 1
            string_builder += '\n'
            if (k1 + 1) % self.root == 0 and (k1 + 1) != self.size:
                string_builder += ("-" * (2 * self.size + 2 * self.root - 3))
                string_builder += '\n'
            k1 += 1
        return string_builder

    def load(self, file_name):
        with open(file_name, "r") as file:
            k = 0
            for line in file:
                self.matrix[k] = line.split()
                k += 1
        for i in range(self.size):
            for j in range(self.size):
                self.matrix[i][j] = int(self.matrix[i][j])

    def placeable(self, i, j, k):
        i_min = (i // self.root) * self.root
        j_min = (j // self.root) * self.root
        i_max = i_min + self.root
        j_max = j_min + self.root
        for i1 in range(i_min, i_max):
            for j1 in range(j_min, j_max):
                # print(i1, j1)
                if k == self.matrix[i1][j1]:
                    return False
        for i1 in range(self.size):
            # print(i1)
            if k == self.matrix[i1][j]:
                return False
        for j1 in range(self.size):
            # print(j1)
            if k == self.matrix[i][j1]:
                return False
        return True

    def __eq__(self, other):
        for i in range(self.size):
            for j in range(self.size):
                if self.matrix[i][j] != other.get_value(i, j):
                    return False
        return True
