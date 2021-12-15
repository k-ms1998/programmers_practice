#다리를 지나는 트럭 (Level 2)

# bridge_length = 2
# weight = 10
# truck_weights = [7,4,5,6]

# bridge_length = 100
# weight = 100
# truck_weights = [10]

bridge_length = 100
weight = 100
truck_weights = [10,10,10,10,10,10,10,10,10,10]

from collections import deque

def solution(bridge_length, weight, truck_weights):
    answer = 0

    bridge = deque([0]*bridge_length)
    curW = 0

    #남아 있는 트럭 수를 기준으로 풀때
    while truck_weights:
        # print(bridge, truck_weights, curL, answer)
        b = bridge.popleft()
        curW -= b
        answer += 1
        
        if truck_weights[0] + curW <= weight:
            t = truck_weights.pop(0)
            curW += t
            bridge.append(t)
        else:
            bridge.append(0)
    answer += bridge_length


    # bridge 기준으로 풀떄
    # while bridge:
    #     answer += 1
    #     curW -= bridge.popleft()
    #     if truck_weights:
    #         if truck_weights[0] + curW <= weight:
    #             t = truck_weights.pop(0)
    #             bridge.append(t)
    #             curW += t
    #         else:
    #             bridge.append(0)


    return answer

print(solution(bridge_length, weight, truck_weights))