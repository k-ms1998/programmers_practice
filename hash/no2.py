#phone_book = ["119", "97674223", "1195524421"]
#phone_book = ["123","456","789"]
#phone_book = ["12","1235", "123","567","88"]
#phone_book = ["1235", "123","567","88", "5"]
phone_book = ["1237", "45", "123"]

def solution(phone_book):
    answer = True

    pSize = len(phone_book)
    for i in range(pSize):
        for j in range(pSize):
            if i == j or phone_book[j][0] != phone_book[i][0]:
                continue
            
            if phone_book[j][0:len(phone_book[i])] == phone_book[i]:
                return False  


    return answer

print(solution(phone_book))