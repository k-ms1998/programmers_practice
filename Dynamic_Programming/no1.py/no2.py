#정수 삼각형 (Level 3)

triangle = [[7], [3, 8], [8, 1, 0], [2, 7, 4, 4], [4, 5, 2, 6, 5]]

def solution(triangle):
    while True:
        h = triangle.pop(0)
        if not triangle:
            return max(h)
        for i in range(len(h)+1):
            if i == 0:
                triangle[0][i] += h[0]
            elif i == len(h):
                triangle[0][i] += h[len(h)-1]
            else:
                triangle[0][i] += max(h[i],h[i-1])    

print(solution(triangle))