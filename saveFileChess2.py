import pygame
from random import randint


pygame.init()
WIDTH = 1200
HEIGHT= 800
gameWindow=pygame.display.set_mode((WIDTH,HEIGHT))
pygame.event.clear()


# // Initialize Images \\ #
imagePath = "C:/Users/dkaiz/Documents/python/chess/"
whiteKing = pygame.image.load(imagePath + "White King.png").convert_alpha()
whiteKing = pygame.transform.smoothscale(whiteKing, (100, 100))
whiteQueen = pygame.image.load(imagePath + "White Queen.png").convert_alpha()
whiteQueen = pygame.transform.smoothscale(whiteQueen, (100, 100))
whiteRook = pygame.image.load(imagePath + "White Rook.png").convert_alpha()
whiteRook = pygame.transform.smoothscale(whiteRook, (100, 100))
whiteBishop = pygame.image.load(imagePath + "White Bishop.png").convert_alpha()
whiteBishop = pygame.transform.smoothscale(whiteBishop, (100, 100))
whiteKnight = pygame.image.load(imagePath + "White Knight.png").convert_alpha()
whiteKnight = pygame.transform.smoothscale(whiteKnight, (100, 100))
whitePawn = pygame.image.load(imagePath + "White Pawn.png").convert_alpha()
whitePawn = pygame.transform.smoothscale(whitePawn, (100, 100))

blackKing = pygame.image.load(imagePath + "Black King.png").convert_alpha()
blackKing = pygame.transform.smoothscale(blackKing, (100, 100))
blackQueen = pygame.image.load(imagePath + "Black Queen.png").convert_alpha()
blackQueen = pygame.transform.smoothscale(blackQueen, (100, 100))
blackRook = pygame.image.load(imagePath + "Black Rook.png").convert_alpha()
blackRook = pygame.transform.smoothscale(blackRook, (100, 100))
blackBishop = pygame.image.load(imagePath + "Black Bishop.png").convert_alpha()
blackBishop = pygame.transform.smoothscale(blackBishop, (100, 100))
blackKnight = pygame.image.load(imagePath + "Black Knight.png").convert_alpha()
blackKnight = pygame.transform.smoothscale(blackKnight, (100, 100))
blackPawn = pygame.image.load(imagePath + "Black Pawn.png").convert_alpha()
blackPawn = pygame.transform.smoothscale(blackPawn, (100, 100))




board = [[["", ""] for x in range(8)] for y in range(8)] 

blackPieces = "rnbqkbnr"
whitePieces = "RNBQKBNR"


#board[0][0][0] = blackPieces[0]
for count in range(8):
    board[0][count][0] = blackPieces[count]
    board[0][count][1] = "b"
    board[1][count][0] = "p"
    board[1][count][1] = "b"
    board[7][count][0] = whitePieces[count]
    board[7][count][1] = "w"
    board[6][count][0] = "P"
    board[6][count][1] = "w"
    

#print (board)

for i in range(8):
    print (board[i])

def drawBoard():
    # draws the board
    for i in range(8):
        for j in range(8):
            if (i + j) % 2 == 0:
                color = (240, 217, 183)
            else:
                color = (180, 136, 102)
            pygame.draw.rect(gameWindow, color, (100 * j, 100 * i, 100, 100), 0)
    drawPieces(board)


def drawPieces(board):
    for i in range(8):
        for j in range(8):
            if board[i][j][0] != "":
                if board[i][j][0] == "r":
                    gameWindow.blit(blackRook, (j * 100, i * 100))
                elif board[i][j][0] == "n":
                    gameWindow.blit(blackKnight, (j * 100, i * 100))
                elif board[i][j][0] == "b":
                    gameWindow.blit(blackBishop, (j * 100, i * 100))
                elif board[i][j][0] == "q":
                    gameWindow.blit(blackQueen, (j * 100, i * 100))
                elif board[i][j][0] == "k":
                    gameWindow.blit(blackKing, (j * 100, i * 100))
                elif board[i][j][0] == "p":
                    gameWindow.blit(blackPawn, (j * 100, i * 100))
                elif board[i][j][0] == "R":
                    gameWindow.blit(whiteRook, (j * 100, i * 100))
                elif board[i][j][0] == "B":
                    gameWindow.blit(whiteBishop, (j * 100, i * 100))
                elif board[i][j][0] == "N":
                    gameWindow.blit(whiteKnight, (j * 100, i * 100))
                elif board[i][j][0] == "Q":
                    gameWindow.blit(whiteQueen, (j * 100, i * 100))
                elif board[i][j][0] == "K":
                    gameWindow.blit(whiteKing, (j * 100, i * 100))
                elif board[i][j][0] == "P":
                    gameWindow.blit(whitePawn, (j * 100, i * 100))
    
    
def checkColumn(index): # completed
    global board
    piecesInColumn = ""
    for count in range(8):
        if board[count][int(index[0])][0] != "" and count != int(index[1]):
            piecesInColumn = piecesInColumn + board[count][int(index[0])][0] + index[0] + str(count)

    return piecesInColumn

def checkRow(index): #completed
    piecesInRow = ""
    for count in range(8):
        if board[int(index[1])][count][0] != "" and count != int(index[0]):
            piecesInRow = piecesInRow + board[int(index[1])][count][0] + str(count) + index[1] 

    return piecesInRow

def checkLeftDiagonal(index): #idk if works
    piecesInLeftDiagonal = ""
    shortestLengthTop = compareNum(int(index[0]), int(index[1]))
    shortestLengthBottom = compareNum(7 - int(index[0]), 7 - int(index[1]))
    for count in range(shortestLengthTop + shortestLengthBottom + 1):
        x = int(index[0]) - shortestLengthTop + count
        y = int(index[1]) - shortestLengthTop + count
        if board[y][x][0] != "" and int(index[0]) != count:
            pygame.draw.rect(gameWindow, (255, 0, 0), (100 * x, 100 * y, 100, 100), 0)
            pygame.event.clear()
            pygame.display.update()
            piecesInLeftDiagonal = piecesInLeftDiagonal + board[int(index[0]) - shortestLengthTop + count][int(index[1]) - shortestLengthTop + count][0] + str(int(index[0]) - shortestLengthTop + count) + str(int(index[1]) - shortestLengthTop + count)

    #return piecesInLeftDiagonal

def checkRightDiagonal(index):
    piecesInRightDiagonal = ""
    shortestLengthTop = compareNum(7 - int(index[0]), int(index[1]))
    print (8 - int(index[0]))
    print (shortestLengthTop)
    shortestLengthBottom = compareNum(int(index[0]), 7 - int(index[1]))
    print (shortestLengthBottom)
    
    for count in range(shortestLengthTop + shortestLengthBottom + 1):
        x = int(index[0]) + shortestLengthTop - count
        y = int(index[1]) - shortestLengthTop + count
        if board[y][x][0] != "" and int(index[0]) != count:
            pygame.draw.rect(gameWindow, (255, 0, 0), (100 * x, 100 * y, 100, 100), 0)
            pygame.event.clear()
            pygame.display.update()
            piecesInRightDiagonal = piecesInRightDiagonal + board[y][x][0] + str(int(index[0]) - shortestLengthTop + count) + str(int(index[1]) - shortestLengthTop + count)

    #return piecesInRightDiagonal
    



def slidingPiece(index, direction):
    x = int(index[0])
    y = int(index[1])
    print (turnPlayer)
    if direction == "left" and x > 0 and board[y][x - 1][1] != turnPlayer:
        return str(x - 1) + str(y) + slidingPiece(str(x - 1) + str(y), "left")
    if direction == "right" and x < 7 and board[y][x + 1][1] != turnPlayer:
        return str(x + 1) + str(y) + slidingPiece(str(x + 1) + str(y), "right")
    if direction == "up" and y > 0 and board[y - 1][x][1] != turnPlayer:
        return str(x) + str(y - 1) + slidingPiece(str(x) + str(y - 1), "up")
    if direction == "down" and y < 7 and board[y + 1][x][1] != turnPlayer:
        return str(x) + str(y + 1) + slidingPiece(str(x) + str(y + 1), "down")
    else:
        return ""




def diagonalPieces(index, direction):
    x = int(index[0])
    y = int(index[1])
    if direction == "leftup" and x > 0 and y > 0 and board[y - 1][x - 1][1] != turnPlayer:
        return str(x - 1) + str(y - 1) + diagonalPieces(str(x - 1) + str(y - 1), "leftup")
    if direction == "rightup" and x < 7 and y > 0 and board[y - 1][x + 1][1] != turnPlayer:
        return str(x + 1) + str(y - 1) + diagonalPieces(str(x + 1) + str(y - 1), "rightup")
    if direction == "leftdown" and x > 0 and y < 7 and board[y + 1][x - 1][1] != turnPlayer:
        return str(x - 1) + str(y + 1) + diagonalPieces(str(x - 1) + str(y + 1), "leftdown")
    if direction == "rightdown" and x < 7 and y < 7 and board[y + 1][x + 1][1] != turnPlayer:
        return str(x + 1) + str(y + 1) + diagonalPieces(str(x + 1) + str(y + 1), "rightdown")
    else:
        return ""
    

def knightPieces(index):
    x = int(index[0])
    y = int(index[1])
    moves = ""
    if x > 0 and y < 6 and board[y + 2][x - 1][1] != turnPlayer:
        moves += str(x - 1) + str(y + 2)
    if x < 7 and y < 6 and board[y + 2][x + 1][1] != turnPlayer:
        moves += str(x + 1) + str(y + 2)
    if x > 1 and y < 7 and board[y + 1][x - 2][1] != turnPlayer:
        moves += str(x - 2) + str(y + 1)
    if x < 6 and y < 7 and board[y + 1][x + 2][1] != turnPlayer:
        moves += str(x + 2) + str(y + 1)
    if x > 1 and y > 0 and board[y - 1][x - 2][1] != turnPlayer:
        moves += str(x - 2) + str(y - 1)
    if x < 6 and y > 0 and board[y  - 1][x + 2][1] != turnPlayer:
        moves += str(x + 2) + str(y - 1)
    if x > 0 and y > 1 and board[y - 2][x - 1][1] != turnPlayer:
        moves += str(x - 1) + str(y - 2)
    if x < 7 and y > 1 and board[y - 2][x + 1][1] != turnPlayer:
        moves += str(x + 1) + str(y - 2)

    return moves

def kingMove(index, attackingSquares):
    x = int(index[0])
    y = int(index[1])
    moves = ""
    if x > 0 and board[y][x - 1][1] != turnPlayer and str(x - 1) + str(y) not in attackingSquares:
        moves += str(x - 1) + str(y)
    if x < 7 and board[y][x + 1][1] != turnPlayer and str(x + 1) + str(y) not in attackingSquares:
        moves += str(x + 1) + str(y)
    if y > 0 and board[y - 1][x][1] != turnPlayer and str(x) + str(y - 1) not in attackingSquares:
        moves += str(x) + str(y - 1)
    if y < 7 and board[y + 1][x][1] != turnPlayer and str(x) + str(y + 1) not in attackingSquares:
        moves += str(x) + str(y + 1)
    if x > 0 and y > 0 and board[y - 1][x - 1][1] != turnPlayer and str(x - 1) + str(y - 1) not in attackingSquares:
        moves += str(x - 1) + str(y - 1)
    if x < 7 and y > 0 and board[y - 1][x + 1][1] != turnPlayer and str(x + 1) + str(y - 1) not in attackingSquares:
        moves += str(x + 1) + str(y - 1)
    if x > 0 and y < 7 and board[y + 1][x - 1][1] != turnPlayer and str(x - 1) + str(y + 1) not in attackingSquares:
        moves += str(x - 1) + str(y + 1)
    if x < 7 and y < 7 and board[y + 1][x + 1][1] != turnPlayer and str(x + 1) + str(y + 1) not in attackingSquares:
        moves += str(x + 1) + str(y + 1)
    return moves

def slidingPieceCapture(index, direction):
    x = int(index[0])
    y = int(index[1])
    print (turnPlayer)
    if direction == "left" and x > 0:
        if board[y][x - 1][1] == turnPlayer:
            return str(x - 1) + str(y)
        else:        
            return str(x - 1) + str(y) + slidingPieceCapture(str(x - 1) + str(y), "left")
    if direction == "right" and x < 7:
        if board[y][x + 1][1] == turnPlayer:
            return str(x + 1) + str(y)
        else:
            return str(x + 1) + str(y) + slidingPieceCapture(str(x + 1) + str(y), "right")
    if direction == "up" and y > 0:
        if board[y - 1][x][1] == turnPlayer:
            return str(x) + str(y - 1)
        else:
            return str(x) + str(y - 1) + slidingPieceCapture(str(x) + str(y - 1), "up")
    if direction == "down" and y < 7:
        if board[y + 1][x][1] == turnPlayer:
            return str(x) + str(y + 1)
        else:
            return str(x) + str(y + 1) + slidingPieceCapture(str(x) + str(y + 1), "down")
    else:
        return ""

def diagonalPieceCapture(index, direction):
    x = int(index[0])
    y = int(index[1])
    if direction == "leftup" and x > 0 and y > 0:
        if board[y - 1][x - 1][1] == turnPlayer:
            return str(x - 1) + str(y - 1)
        else:
            return str(x - 1) + str(y - 1) + diagonalPieceCapture(str(x - 1) + str(y - 1), "leftup")
    if direction == "rightup" and x < 7 and y > 0:
        if board[y - 1][x + 1][1] == turnPlayer:
            return str(x + 1) + str(y - 1)
        else:
            return str(x + 1) + str(y - 1) + diagonalPieceCapture(str(x + 1) + str(y - 1), "rightup")
    if direction == "leftdown" and x > 0 and y < 7:
        if board[y + 1][x - 1][1] == turnPlayer:
            return str(x - 1) + str(y + 1)
        else:
            return str(x - 1) + str(y + 1) + diagonalPieceCapture(str(x - 1) + str(y + 1), "leftdown")
    if direction == "rightdown" and x < 7 and y < 7:
        if board[y + 1][x + 1][1] == turnPlayer:
            return str(x + 1) + str(y + 1)
        else:
            return str(x + 1) + str(y + 1) + diagonalPieceCapture(str(x + 1) + str(y + 1), "rightdown")
    else:
        return ""

def knightCapture(index):
    x = int(index[0])
    y = int(index[1])
    moves = ""
    if x > 0 and y < 6:
        moves += str(x - 1) + str(y + 2)
    if x < 7 and y < 6:
        moves += str(x + 1) + str(y + 2)
    if x > 1 and y < 7:
        moves += str(x - 2) + str(y + 1)
    if x < 6 and y < 7:
        moves += str(x + 2) + str(y + 1)
    if x > 1 and y > 0:
        moves += str(x - 2) + str(y - 1)
    if x < 6 and y > 0:
        moves += str(x + 2) + str(y - 1)
    if x > 0 and y > 1:
        moves += str(x - 1) + str(y - 2)
    if x < 7 and y > 1:
        moves += str(x + 1) + str(y - 2)

    return moves



def kingCapture(index):
    x = int(index[0])
    y = int(index[1])
    moves = ""
    if x > 0:
        moves += str(x - 1) + str(y)
    if x < 7:
        moves += str(x + 1) + str(y)
    if y > 0:
        moves += str(x) + str(y - 1)
    if y < 7:
        moves += str(x) + str(y + 1)
    if x > 0 and y > 0:
        moves += str(x - 1) + str(y - 1)
    if x < 7 and y > 0:
        moves += str(x + 1) + str(y - 1)
    if x > 0 and y < 7:
        moves += str(x - 1) + str(y + 1)
    if x < 7 and y < 7:
        moves += str(x + 1) + str(y + 1)
    return moves




def blackLegalMoves():
    


def generateLegalMoves(index, piece, captures):
    global turnPlayer
    moves = ""
    if piece[0] == "P":
        if not captures and board[int(index[1]) - 1][int(index[0])][0] == "":
            moves = moves + index[0] + str(int(index[1]) - 1)
        if not captures and index[1] == "6" and board[int(index[1]) - 2][int(index[0])][0] == "" and board[int(index[1]) - 1][int(index[0])][0] == "":
            moves = moves + index[0] + str(int(index[1]) - 2)
        if int(index[0]) > 0 and board[int(index[1]) - 1][int(index[0]) - 1][1] == "b":
           moves = moves + str(int(index[0]) - 1) + str(int(index[1]) - 1)
        if int(index[0]) < 7 and board[int(index[1]) - 1][int(index[0]) + 1][1] == "b":
            moves = moves + str(int(index[0]) + 1) + str(int(index[1]) - 1)

        if not captures:
            moves += index

        if captures and int(index[0]) > 0:
           moves = moves + str(int(index[0]) - 1) + str(int(index[1]) - 1)
        if captures and int(index[0]) < 7:
            moves = moves + str(int(index[0]) + 1) + str(int(index[1]) - 1)


    


    if piece[0] == "R" or piece[0] == "r":
        moves = moves + slidingPiece(index, "left") + slidingPiece(index, "right") + slidingPiece(index, "up") + slidingPiece(index, "down") + index

    if piece[0] == "B" or piece[0] == "b":
        moves = moves + diagonalPieces(index, "leftup") + diagonalPieces(index, "rightup") + diagonalPieces(index, "leftdown") + diagonalPieces(index, "rightdown") + index

    if piece[0] ==  "Q" or piece[0] == "q":
        moves = moves + slidingPiece(index, "left") + slidingPiece(index, "right") + slidingPiece(index, "up") + slidingPiece(index, "down") + diagonalPieces(index, "leftup") + diagonalPieces(index, "rightup") + diagonalPieces(index, "leftdown") + diagonalPieces(index, "rightdown") + index

    if piece[0] == "N" or piece[0] == "n":
        moves = knightPieces(index) + index


    if piece[0] == "K":
        legalMoves = []
        turnPlayer = "b"
        for i in range(8):
            for j in range(8):
                move = ""
                square = str(j) + str(i)
                if board[i][j][0] == "r":
                    move = slidingPieceCapture(square, "left") + slidingPieceCapture(square, "right") + slidingPieceCapture(square, "up") + slidingPieceCapture(square, "down")

                elif board[i][j][0] == "b":
                    move = diagonalPieceCapture(square, "leftup") + diagonalPieceCapture(square, "rightup") + diagonalPieceCapture(square, "leftdown") + diagonalPieceCapture(square, "rightdown")

                elif board[i][j][0] == "q":
                    move = slidingPieceCapture(square, "left") + slidingPieceCapture(square, "right") + slidingPieceCapture(square, "up") + slidingPieceCapture(square, "down")
                    move = move + diagonalPieceCapture(square, "leftup") + diagonalPieceCapture(square, "rightup") + diagonalPieceCapture(square, "leftdown") + diagonalPieceCapture(square, "rightdown")

                elif board[i][j][0] == "n":
                    move = knightCapture(square)

                elif board[i][j][0] == "k":
                    move = kingCapture(square)

                elif board[i][j][0] == "p":
                    move = generateLegalMoves(square, board[i][j], True)

                for count in range(int(len(move)/2)):
                    legalMoves.append(move[count * 2] + move[count * 2 + 1])
                    
        turnPlayer = "w"
        moves = kingMove(index, legalMoves) + index
    
    if piece[0] == "k":
        legalMoves = []
        turnPlayer = "w"
        for i in range(8):
            for j in range(8):
                move = ""
                square = str(j) + str(i)
                if board[i][j][0] == "R":
                    move = slidingPieceCapture(square, "left") + slidingPieceCapture(square, "right") + slidingPieceCapture(square, "up") + slidingPieceCapture(square, "down")

                elif board[i][j][0] == "B":
                    move = diagonalPieceCapture(square, "leftup") + diagonalPieceCapture(square, "rightup") + diagonalPieceCapture(square, "leftdown") + diagonalPieceCapture(square, "rightdown")

                elif board[i][j][0] == "Q":
                    move = slidingPieceCapture(square, "left") + slidingPieceCapture(square, "right") + slidingPieceCapture(square, "up") + slidingPieceCapture(square, "down")
                    move = move + diagonalPieceCapture(square, "leftup") + diagonalPieceCapture(square, "rightup") + diagonalPieceCapture(square, "leftdown") + diagonalPieceCapture(square, "rightdown")

                elif board[i][j][0] == "N":
                    move = knightCapture(square)

                elif board[i][j][0] == "K":
                    move = kingCapture(square)

                elif board[i][j][0] == "P":
                    move = generateLegalMoves(square, board[i][j], True)


                for count in range(int(len(move)/2)):
                    legalMoves.append(move[count * 2] + move[count * 2 + 1])
                    
        turnPlayer = "b"
        moves = kingMove(index, legalMoves) + index
    
    if piece[0] == "p":
        if not captures and board[int(index[1]) + 1][int(index[0])][0] == "":
            moves = moves + index[0] + str(int(index[1]) + 1)
        if not captures and index[1] == "1" and board[int(index[1]) + 2][int(index[0])][0] == "" and board[int(index[1]) + 1][int(index[0])][0] == "":
            moves = moves + index[0] + str(int(index[1]) + 2)
        if int(index[0]) > 0 and board[int(index[1]) + 1][int(index[0]) - 1][1] == "w":
            moves = moves + str(int(index[0]) - 1) + str(int(index[1]) + 1)
        if int(index[0]) < 7 and board[int(index[1]) + 1][int(index[0]) + 1][1] == "w":
            moves = moves + str(int(index[0]) + 1) + str(int(index[1]) + 1)

        if not captures:
            moves += index


        if captures and int(index[0]) > 0:
           moves = moves + str(int(index[0]) - 1) + str(int(index[1]) + 1)
        if captures and int(index[0]) < 7:
            moves = moves + str(int(index[0]) + 1) + str(int(index[1]) + 1)




#    for count in range(int((len(moves) - 2)/2)):
#        if not kingInCheck(moves[count * 2] + moves[count * 2 + 1], index):
#            newMoves += moves[count * 2] + moves[count * 2 + 1]

    return moves




def kingInCheck(move, currentPos):
    newBoard = board
    x = int(move[0])
    y = int(move[1])
    currentPosX = int(currentPos[0])
    currentPosY = int(currentPos[1])
    newBoard[y][x] = newBoard[currentPosY][currentPosX]
    newBoard[currentPosY][currentPosX] = ["", ""]

    #new board is updated

    


    # updates board position in new board
    # finds king and loops through all moves for opposite color
    # checks if any of those moves can capture the king
    

    
        

        
#            board[int(index[0]) - 1][int(index[1]) - 1][0] = ""
#            kingCheck(board, color, kingPos)
            


def compareNum(a, b):
    if a > b:
        return b
    else:
        return a

#def checkPins(kingPosW, kingPosB):
#    global board
#
#    if 
    

drawBoard()
drawPieces(board)
turnPlayer = "w"
selectedMoves = False
delay = 100

                                                                                                                        #####################
                                                                                                                        #                   #
                                                                                                                        # --- MAIN CODE --- #
                                                                                                                        #                   #
                                                                                                                        ##################### 

while True:
    delay += 1
    if pygame.mouse.get_pressed() == (1, 0, 0) and delay > 100:
        delay = 0
        (x, y) = pygame.mouse.get_pos()
        
        cellX = int(x/100)
        cellY = int(y/100)
        print (cellX, cellY)
        justMoved = False
        if cellX < 8:
            if selectedMoves:
                for count in range(int((len(moves) - 2)/2)):
                    if int(moves[count * 2]) == cellX and int(moves[count * 2 + 1]) == cellY:
                        board[int(moves[count * 2 + 1])][int(moves[count * 2])] = board[int(moves[len(moves) - 1])][int(moves[len(moves) - 2])]
                        board[int(moves[len(moves) - 1])][int(moves[len(moves) - 2])] = ["", ""]
                        justMoved = True
                        if turnPlayer == "w":
                            turnPlayer = "b"
                        else:
                            turnPlayer = "w"
                        
            if board[cellY][cellX][1] == turnPlayer and not justMoved:
                selectedMoves = True
                drawBoard()
    #            print (board[cellY][cellX][0], turnPlayer)
                for i in range(8):
                    print (board[i])
                print (cellX, cellY, board[cellY][cellX][0])
                moves = generateLegalMoves(str(cellX) + str(cellY), board[cellY][cellX][0], False)
                
                for count in range(int((len(moves) - 2)/2)):
                    pygame.draw.rect(gameWindow, (255, 0, 0), (int(moves[count * 2]) * 100, int(moves[count * 2 + 1]) * 100, 100, 100), 0)
                drawPieces(board)

            else:
                selectedMoves = False
                drawBoard()
            

            
        # for event in pygame.event.get():
#    drawPieces(board)
    pygame.display.update()
    pygame.event.clear()
