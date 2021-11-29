phone_book = ["119", "97674223", "1195524421"]
#phone_book = ["123","456","789"]
#phone_book = ["12","123","1235","567","88"]

def solution(phone_book):
    answer = True

    pSize = len(phone_book)
    for i in range(pSize):
        ppSize = len(phone_book[i])
        for j in range(pSize):
            if i == j:
                continue
      
            pppSize = len(phone_book[j])
            
            if ppSize > pppSize:
                continue

            if phone_book[j][0:ppSize] == phone_book[i]:
                return False  
    
    return answer

print(solution(phone_book))