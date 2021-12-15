#t = "3+4*5"
# t = "2*5+6/2-4"
t = "1+8/2-(2*2)"

l = []
s = []
#s[-1] = top
for i in range(len(t)):
    print(l)
    if t[i] == '(':
        s.append(t[i])
    elif t[i] == ')':
        while True:
            tmp = s.pop()
            if tmp == '(':
                break
            l.append(tmp)
    elif t[i].isdigit():
        l.append(t[i])
    elif t[i] == '+' or t[i] == '-':
        while s and s[-1] != '(':
            l.append(s.pop())
        s.append(t[i])
    elif t[i] == '*' or t[i] == '/':
        while s and (s[-1] == '*' or s[-1] == '/'):
            l.append(s.pop())
        s.append(t[i])    
    else:
        s.append(t[i])

while s:
    l.append(s.pop())
print(l)
