clothes = [["yellowhat", "headgear"], ["bluesunglasses", "eyewear"], ["green_turban", "headgear"]]
#clothes =[["crowmask", "face"], ["bluesunglasses", "face"], ["smoky_makeup", "face"]]
#clothes = [["yellowhat", "headgear"], ["bluesunglasses", "eyewear"], ["glasses", "eyewear"], ["green_turban", "headgear"], ["running", "shoe"], ["boots", "shoe"], ["sneakers", "shoe"]]

def solution(clothes):
    answer = 0

    hash = {}
    for c in clothes:
        if c[1] in hash:
            hash[c[1]].append(c[0])
        else:
            hash[c[1]] = [c[0]]

    answer = 1
    for h in hash:
        #len(hash[h]) + 1 ==> 의상 종류의 개수 + 의상을 입지 않은 경우
        answer *= (len(hash[h])+1)
    
    #적어도 하나의 의상은 입어야하기 때문에, 의상을 하나도 입지 않은 경우 제외
    answer -= 1
    

    return answer

print(solution(clothes))