#타켓 넘버(Level 2)
numbers = [1, 1, 1, 1, 1]
target = 3


def dfs(numbers, target, sum, cnt):
    #Deep Copy
    arr = [i for i in numbers]
    #print(arr, target, sum, cnt)
    if numbers:
        c = arr.pop()
        cnt = dfs(arr, target, sum+c, cnt)
        cnt = dfs(arr, target, sum-c, cnt)
    else:
        if target == sum:
            #print("FINAL:",arr, target, sum, cnt)
            return cnt+1
        else:
            return cnt

    return cnt

def solution(numbers, target):
    return dfs(numbers, target, 0, 0)

print(solution(numbers, target))