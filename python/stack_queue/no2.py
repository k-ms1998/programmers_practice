#프린터 (Level 2)

priorities = [1, 1, 9, 1, 1, 1]
location = 0

# priorities = [2, 1, 3, 2]
# location = 2

def solution(priorities, location):
    queue = []
    pos = [i for i in range(len(priorities))]
    fPos = []
    while priorities:
        flag = 0
        cur = priorities.pop(0)
        curPos = pos.pop(0)
        for p in priorities:
            if p > cur:
                priorities.append(cur)
                pos.append(curPos)
                flag = 1
                break
        if flag == 0:
            fPos.append(curPos)
            queue.append(cur)

    for i in range(len(fPos)):
        if fPos[i] == location:
            return i+1


print(solution(priorities, location))