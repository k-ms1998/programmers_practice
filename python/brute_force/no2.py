#소수 찾기 (Level 2)

# numbers = "17"
numbers = "011"

from itertools import permutations
import math

def checkPrimeC(nums):
    #에라토스테네스의 체
    size = max(nums)+1

    arr = [0 for _ in range(size)]
    for i in range(size):
        if i < 2:
            arr[i] = 1
            continue
        
        if arr[i] == 0:
            m = 2
            # m must start from 2; If i starts from 1, then every arr[idx] == 1
            # ex) if m start from 1, arr[17] == 1
            while True:
                idx = i*m
                if idx >= size:
                    break
                arr[idx] = 1
                m += 1

    cnt = 0
    for n in (nums):
        if arr[n] == 0:
            cnt += 1
    return cnt


def checkPrimeB(n):
    #제곱근
    if n <= 1:
        return 0
    for i in range(2, int(math.sqrt(n))+1):
        if n%i == 0:
            return 0
    
    return 1

def checkPrime(n):
    if n <= 1:
        return 0

    for i in range(2,n):
        if n%i == 0:
            return 0
    return 1

def solution(numbers):
    answer = 0

    size = len(numbers)
    comb = []
    for i in range(1, size+1):
        arr = list(permutations(numbers, i))
        #arr = ("0"), ("1"), ("1"), ("0","1"), ("0","1"), ("1","0"), ("1","1")....("0","1","1")
        for j in range(len(arr)):
            cur = int(''.join(map(str, arr[j])))
            # ("1", "0") -> 10 ...
            if cur not in comb:
                comb.append(cur)

    # for c in comb:
    #     # answer += checkPrime(c)
    #     answer += checkPrimeB(c)

    answer = checkPrimeC(comb)

    return answer

print(solution(numbers))
