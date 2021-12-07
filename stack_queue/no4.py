#주식 가격 (Level 2)

prices = [1,2,3,2,3]
prices = [1,2,3,2,3,1]

from collections import deque

def solution(prices):
    size = len(prices)
    answer = [i for i in range(size-1, -1, -1)]
    
    stack = []
    for i in range(size):
        # print(i,":",stack)
        while stack:
            idx = stack[-1]
            if prices[idx] > prices[i]:
                #가격이 떨어지는 시점
                #print("Flag:", prices[idx], prices[i], stack, idx, i)
                stack.pop()
                answer[idx] = i - idx
            else:
                break
        stack.append(i)

    return answer


# def solution(prices):
#     answer = []
#     prices = deque(prices)

#     while prices:
#         cp = prices.popleft()
#         cnt = 0
#         for p in prices:
#             cnt += 1
#             if p < cp:
#                 break
#         answer.append(cnt)


#     return answer

print(solution(prices))