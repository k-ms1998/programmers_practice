#단어 변환 (Level 3)

# begin = "hit"
# target = "cog"
# words = ["hot", "dot", "dog", "lot", "log", "cog"]
# words = ["hot", "dot", "dog", "lot", "log"]

begin = "hit"
target = "hhh"
words = ["hhh","hht"]

def cmpWords(cur, next):
    size = len(cur)
    flag = 0
    for i in range(size):
        if cur[i] != next[i]:
            if flag == 0:
                flag += 1
                continue
            else:
                return 0

    if flag == 1:
        return 1
        

def dfs(cur, next, target, cnt, words, v, pos):
    if cur == target:
        return cnt
    elif cmpWords(cur, target):
        return cnt+1
    elif cmpWords(cur, next):
        return dfs(next, words[pos+1], target, cnt+1, words, v, pos+1)
    elif not cmpWords(cur, next):
        return dfs(cur, words[pos+1], target, cnt, words, v, pos+1)
    elif pos+1 == len(words)-1:
        return -1

    return cnt

def solution(begin, target, words):
    answer = 0

    if target in words:
        tMin = len(words)
        for i in range(len(words)):
            v = [0]*len(words)
            v[i] = 1
            if cmpWords(begin, words[i]):
                tmp = dfs(begin, words[i], target, 0, words, v, 0)
                #print("Tmp:", tmp, begin, words[i])
                if tmp == -1:
                    continue
                if tmp < tMin:
                    tMin = tmp

        answer = tMin
    
    return answer

print(solution(begin, target, words))
