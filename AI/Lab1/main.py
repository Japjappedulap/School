import pprint


class State:
    def __init__(self, size=2, loading_file=None):
        self.__square = size ** 2
        self.__root = size
        self.__matrix = [[0 for _x in range(self.__square)] for _y in range(self.__square)]
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
                if (k2 + 1) % self.__root == 0 and (k2 + 1) != self.__square:
                    string_builder += "| "
                k2 += 1
            string_builder += '\n'
            if (k1 + 1) % self.__root == 0 and (k1 + 1) != self.__square:
                string_builder += ("-" * (2 * self.__square + 2 * self.__root - 3))
                string_builder += '\n'
            k1 += 1
        return string_builder

    def load(self, file_name):
        with open(file_name, "r") as file:
            k = 0
            for line in file:
                self.__matrix[k] = line.split()
                k += 1


if __name__ == '__main__':
    x = State(2, "in.txt")
    
    print(x)
