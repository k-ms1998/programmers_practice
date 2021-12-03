#여행경로 (Level 3)


# tickets = [["ICN", "JFK"], ["HND", "IAD"], ["JFK", "HND"]]
tickets = [["ICN", "SFO"], ["ICN", "ATL"], ["SFO", "ATL"], ["ATL", "ICN"], ["ATL","SFO"]]
# tickets = [["ICN", "AOO"], ["AOO", "BOO"], ["BOO", "COO"], ["COO", "DOO"], ["DOO", "EOO"], ["EOO", "DOO"], ["DOO", "COO"], ["COO", "BOO"], ["BOO", "AOO"]]

def solution(tickets):
    answer = []

    hash = {}
    for t in tickets:
        if t[0] not in hash:
            hash[t[0]] = [t[1]]
        else:
            hash[t[0]].append(t[1])
    for h in hash:
        hash[h].sort()

    stack = ["ICN"]
    while stack:
        src = stack.pop(0)
        answer.append(src)
        if src not in hash:
            continue
        if len(hash[src]) != 0:
            stack.append(hash[src].pop())

            
    return answer

print("FINAL:",solution(tickets))