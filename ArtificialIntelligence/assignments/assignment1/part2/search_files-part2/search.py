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
            return [s, f[1],f[2]]
        ns=state.get_next(s)
        for i in ns:
            frontier.insert(f,i)
    return 0

def run(n):
    avgPC = 0
    avgPush = 0
    avgPop = 0
    for i in range(100):
        s=search(n)
        avgPC += len(s[0][1])
        avgPush += s[1]
        avgPop += s[2]
        print([s, avgPC, avgPush, avgPop])
    print("N size = ", n)
    print("Average Number Pushed: ", avgPush/100)
    print("Average Number Popped: ", avgPop/100)
    print("Average Path Cost: ", avgPC/100)
    return 0

run(4)

# Results:
# 
# H1, N size =  4
# Average Number Pushed:  16514.92
# Average Number Popped:  7823.21
# Average Path Cost:  15.67
#
# H2, N size =  4
# Average Number Pushed:  2193.41
# Average Number Popped:  1101.87
# Average Path Cost:  15.57
#
# # 2*H1, N size =  4
# Average Number Pushed:  6666.32
# Average Number Popped:  3128.6
# Average Path Cost:  16.4
#
# 2*H2, N size =  4
# Average Number Pushed:  814.71
# Average Number Popped:  405.36
# Average Path Cost:  16.2
#
# Thus, doubling the hueristic does lower the runtime. However, this comes with a sacriftce of optimality, as reflected in the longer average path costs.


