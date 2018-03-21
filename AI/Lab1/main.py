import copy

import datetime


#
# def bfs(initial_state):
#     queue = [initial_state]
#     while len(queue) != 0:
#         # pick first element from queue
#         current_state = queue.pop(0)
#         print(len(queue))
#         if current_state.completed():
#             return current_state
#         next_states = current_state.expand()
#
#         # check if some of the next states are already in queue
#         # if there are, we do not add them
#         for next_state in next_states:
#             to_add = True
#             for existing_state in queue:
#                 if next_state == existing_state:
#                     to_add = False
#                     break
#             if to_add:
#                 queue.append(next_state)
#
#
# def gbfs(initial_state):
#     queue = [initial_state]
#     previous_len = 0
#     while len(queue) != 0:
#         # pick first element from queue
#         current_state = queue.pop(0)
#         print("%6d    diff: %6d" % (len(queue), len(queue) - previous_len))
#         previous_len = len(queue)
#         if current_state.completed():
#             return current_state
#         next_states = current_state.expand_best()
#
#         # check if some of the next states are already in queue
#         # if there are, we do not add them
#         for next_state in next_states:
#             to_add = True
#             for existing_state in queue:
#                 if next_state == existing_state:
#                     to_add = False
#                     break
#             if to_add:
#                 queue.append(next_state)
#
# def tuned_gbfs(initial_state):
#     queue = [initial_state]
#     previous_len = 0
#     while len(queue) != 0:
#         # pick first element from queue
#         current_state = queue.pop(0)
#         current_state.place_solution()
#
#         print("%6d    diff: %6d, unsolved cells: %2d" % (len(queue), len(queue) - previous_len,
#                                                          current_state.unsolved_cell()))
#         previous_len = len(queue)
#
#         if current_state.completed():
#             return current_state
#         next_states = current_state.expand_best()
#
#         # check if some of the next states are already in queue
#         # if there are, we do not add them
#         for next_state in next_states:
#             to_add = True
#             for existing_state in queue:
#                 if next_state == existing_state:
#                     to_add = False
#                     break
#             if to_add:
#                 queue.append(next_state)
from Controller import Controller

if __name__ == '__main__':
    start_time = datetime.datetime.now()
    x = Controller(3, "in3.txt")
    final_state = x.gbfs()
    print(final_state)
    finish_time = datetime.datetime.now()
    print(finish_time - start_time)
