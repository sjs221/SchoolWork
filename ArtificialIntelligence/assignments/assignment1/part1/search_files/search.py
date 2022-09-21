#search

import state
import frontier

def search(n):
    s=state.create(n)
    # print(s)
    f=frontier.create(s)
    while not frontier.is_empty(f):
        s=frontier.remove(f)
        if state.is_target(s):

            return [s, f[1], f[4], f[5]]

            # return [s, f[1]]
        ns=state.get_next(s)
        #print(ns)
        for i in ns:
            frontier.insert(f,i)
    return 0

# answer=search(2)
# print(answer)

def run(n):
    avgD = 0
    avgPush = 0
    avgPop = 0
    for i in range(0, 100):
        s=search(n)
        avgD += s[1]
        avgPush += s[2]
        avgPop += s[3]
        # print([s, avgD, avgPush, avgPop])
    print("N size = ", n)
    print("Average Depth: ", avgD/100)
    print("Average Number Pushed: ", avgPush/100)
    print("Average number Popped: ", avgPop/100)
    return 0

a=run(2)
b=run(3)
c=run(4)

# Results:
# 
# N size =  2
# Average Depth:  1.81
# Average Number Pushed:  7.5
# Average number Popped:  6.85
# 
# N size =  3
# Average Depth:  6.09
# Average Number Pushed:  1871.74
# Average number Popped:  1075.94
# 
# I was unable to get N size = 4 to give results