s = [0,0]
f = [1,2]
d = [3,4]

#src = (x1, y1)
#dst = (x2, y2)
def getPerm(src, dst):
    dx = 1
    dy = 1
    
    if src[0] > dst[0]:
        dx = -1
    if src[1] > dst[1]:
        dy = -1

    if src[0] > dst[0]:
        maxX = src[0]
        minX = dst[0]
    else:
        maxX = dst[0]
        minX = src[0]

    if src[1] > dst[1]:
        maxY = src[1]
        minY = dst[1]
    else:
        maxY = dst[1]
        minY = src[1]
    sizeX = maxX-minX+1
    sizeY = maxY-minY+1

    mGrid = [[0 for _ in range(sizeX)] for __ in range(sizeY)]
    for i in range(sizeX):
        if i == 0:
            continue
        mGrid[0][i] = 1
    for i in range(sizeY):
        if i == 0:
            continue
        mGrid[i][0] = 1

    for y in range(1,sizeY):
        for x in range(1, sizeX):
            mGrid[y][x] = mGrid[y-dy][x] + mGrid[y][x-dx]
            
    return mGrid[sizeY-1][sizeX-1]


c1 = getPerm(s, f)
c2 = getPerm(f, d)
#print(c1, c2)
print(c1*c2)