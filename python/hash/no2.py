#phone_book = ["119", "97674223", "1195524421"]
#phone_book = ["123","456","789"]
#phone_book = ["12","1235", "123","567","88"]
phone_book = ["1235", "123","567","88", "5"]
#phone_book = ["1237", "45", "123"]

def solution(phone_book):
    answer = True

    hash = {}
    for p in phone_book:
        if p[0] in hash:
            hash[p[0]].append(p)
        else:
            hash[p[0]] = [p]

    for h in hash:
        if len(hash[h]) > 1:
            for i in range(len(hash[h])):
                for j in range(len(hash[h])):
                    if i != j and (hash[h][j].startswith(hash[h][i]) or hash[h][i].startswith(hash[h][j])):
                        return False


    return answer

print(solution(phone_book))