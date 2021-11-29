phone_book = ["119", "97674223", "1195524421"]
#phone_book = ["123","456","789"]
#phone_book = ["12","123","1235","567","88"]

def solution(phone_book):
    answer = True

    pSize = len(phone_book)
    for i in range(pSize):
        for j in range(pSize):
            if i == j:
                continue

            listStdPh = list(phone_book[i])
            listComPh = list(phone_book[j])
                        
            ppSize = len(listStdPh)
            pppSize = len(listComPh)
            if ppSize > pppSize:
                continue


            if listComPh[0:ppSize] == listStdPh:
                answer = False
                return answer    
    
    return answer

print(solution(phone_book))