phone_book = ["119", "97674223", "1195524421"]
#phone_book = ["123","456","789"]
#phone_book = ["12","123","1235","567","88"]

def solution(phone_book):
    answer = True

    pSize = len(phone_book)
    for i in range(pSize):
        listStdPh = list(phone_book[i])
        ppSize = len(listStdPh)
        for j in range(pSize):
            if i == j:
                continue
      
            listComPh = list(phone_book[j])
            pppSize = len(listComPh)
            
            if ppSize > pppSize:
                continue

            if listComPh[0:ppSize] == listStdPh:
                return False  
    
    return answer

print(solution(phone_book))