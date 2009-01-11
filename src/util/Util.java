package util;

/**
 */
public class Util {

    public static void eachCell(int[][] array, ArrayCellCallback callback){
        for (int row = 0;row<array.length;row++){
            for (int col = 0;col<array[0].length;col++){
                callback.cell(row, col);
            }
        }
    }

    public static void eachCell(Object[][] array, ArrayCellCallback callback){
        for (int row = 0;row<array.length;row++){
            for (int col = 0;col<array[0].length;col++){
                callback.cell(row, col);
            }
        }
    }
}
