paricipant = ["leo", "kiki", "eden"]
completion = ["eden", "kiki"]

# paricipant =["marina", "josipa", "nikola", "vinko", "filipa"]
# completion =["josipa", "filipa", "marina", "nikola"]

# paricipant =["mislav", "stanko", "mislav", "ana"]
# completion =["stanko", "ana", "mislav"]

def solution(participant, completion):
    answer = ''
    h = {}

    for p in participant:
        if p in h:
            h[p] += 1
        else:
            h[p] = 1
    
    for c in completion:
        h[c] -= 1
        
    for k in h.keys():
        if h[k] >= 1:
            answer = k
            break    
    
    return answer

print(solution(paricipant, completion))