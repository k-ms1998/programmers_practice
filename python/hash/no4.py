# genres = ["classic", "pop", "classic", "classic", "pop", "classic"]
# plays = [500, 600, 150, 800, 2500, 650]
genres = ["classic", "pop", "classic", "pop", "classic", "classic"]
plays = [400, 600, 150, 600, 500, 500]


def solution(genres, plays):
    answer = []

    size = len(genres)
    hashTotal = {}
    hashP = {}
    hashIdx = {}
    for i in range(size):
        if genres[i] in hashP:
            hashTotal[genres[i]] += plays[i]
            if len(hashP[genres[i]]) < 2:
                hashP[genres[i]].append(plays[i])
                hashIdx[genres[i]].append(i)
                continue
            else:
                cPlays = plays[i]
                hashPA = hashP[genres[i]][0]
                hashPB = hashP[genres[i]][1]
                if hashPA < hashPB:
                    if cPlays > hashPA:
                        hashP[genres[i]][0] = cPlays
                        hashIdx[genres[i]][0] = i
                else:
                    if cPlays > hashPB:
                        hashP[genres[i]][1] = cPlays
                        hashIdx[genres[i]][1] = i
        else:
            hashTotal[genres[i]] = plays[i]
            hashP[genres[i]] = [plays[i]]
            hashIdx[genres[i]] = [i]

    while hashTotal:
        maxK = max(hashTotal, key=hashTotal.get)
        hashTotal.pop(maxK)
        if len(hashP[maxK]) == 2:
            if hashP[maxK][0] > hashP[maxK][1]:
                answer.append(hashIdx[maxK][0])
                answer.append(hashIdx[maxK][1])
            elif hashP[maxK][0] == hashP[maxK][1]:
                if hashIdx[maxK][0] < hashIdx[maxK][1]:
                    answer.append(hashIdx[maxK][0])
                    answer.append(hashIdx[maxK][1])
                else:
                    answer.append(hashIdx[maxK][1])
                    answer.append(hashIdx[maxK][0])
            else:
                answer.append(hashIdx[maxK][1])
                answer.append(hashIdx[maxK][0])
        elif len(hashP[maxK]) == 1:
            answer.append(hashIdx[maxK][0])

    return answer

print(solution(genres, plays))