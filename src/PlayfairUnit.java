public class PlayfairUnit {
    PlayfairUnit(int row1, int row2, int column1, int column2, PlayfairType type) {
        this.row1 = row1;
        this.column1 = column1;
        this.type = type;
        this.row2=row2;
        this.column2=column2;
    }

    int row1,row2,column1, column2;
    PlayfairType type;
}
