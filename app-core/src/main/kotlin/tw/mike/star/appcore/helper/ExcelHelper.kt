package tw.mike.star.appcore.helper

import org.apache.poi.ss.usermodel.*
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.xssf.usermodel.*
import org.springframework.stereotype.Component
import tw.mike.star.appcore.model.user.UserListResp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * excel 匯出工具
 */
@Component
class ExcelHelper {

    private companion object {
        /**
         * 創建基本風格
         * @param fontSize 文字大小
         * @param alignmentCenter 是否置中對齊
         * @param border 是否有編筐
         */
        fun XSSFWorkbook.createBaseStyle(fontSize:Short = 12,alignmentCenter:Boolean = true, border:Boolean = false): XSSFCellStyle {
            val style = this.createCellStyle()

            // 設定樣式屬性
            if (alignmentCenter) { //是否置中對齊
                style.alignment = HorizontalAlignment.CENTER // 水平置中
                style.verticalAlignment = VerticalAlignment.CENTER // 垂直置中
            }
            if (border) { //是否有編筐
                style.borderTop = BorderStyle.THIN // 上邊框
                style.borderBottom = BorderStyle.THIN // 下邊框
                style.borderLeft = BorderStyle.THIN // 左邊框
                style.borderRight = BorderStyle.THIN // 右邊框
            }
//            style.fillForegroundColor = IndexedColors.LIGHT_YELLOW.index // 背景顏色

            // 可再設定字型等其他屬性
            val font = this.createFont()
            font.bold = true // 粗體
            font.fontHeightInPoints = fontSize // 字型大小
            style.setFont(font)

            return style
        }

        /**
         * 依序創建下一列
         */
        fun XSSFSheet.createNextRow(): XSSFRow = this.createRow(this.lastRowNum + 1)

        /**
         * 依序創建下一格
         * @param data 資料 (Double,Boolean,Calendar,LocalDate,LocalDateTime)以外的轉為字串儲存
         * @param style 風格
         */
        fun XSSFRow.createNextCell(data:Any?= null, style: XSSFCellStyle?= null): XSSFCell{
            val index = if (this.lastCellNum.toInt() == -1) 0 else this.lastCellNum.toInt()
            return this.createCell(index).apply {
                this.cellStyle = style
                //依照資料類型儲存
                when (data){
                    null -> setCellValue("")
                    is Double -> setCellValue(data)
                    is Boolean -> setCellValue(data)
                    is LocalDateTime -> setCellValue(data)
                    is LocalDate -> setCellValue(data)
                    is Calendar -> setCellValue(data)
                    else -> setCellValue(data.toString()) //不符合的類型行強制轉成字串儲存
                }
            }
        }
    }

    /**
     * 帳號-查詢多筆並匯出excel。
     */
    fun exportUserList(dataList:List<UserListResp>): XSSFWorkbook {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("帳號列表")
        sheet.defaultRowHeight = 560


        val titleStyle = workbook.createBaseStyle(18)
        val headerStyle = workbook.createBaseStyle(fontSize = 13, border = true)
        val dataStyle = workbook.createBaseStyle(border = true)
        //項目標頭文字清單
        val headerList = listOf("編號","帳號","姓名","角色","狀態","最後登入時間")

        //標題列
        val titleRow = sheet.createNextRow()
        sheet.addMergedRegion(CellRangeAddress(0,0,0,headerList.lastIndex))
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        titleRow.createNextCell("${LocalDateTime.now().format(formatter)} 帳號列表匯出",titleStyle)

        //留白
        sheet.createNextRow()
        sheet.addMergedRegion(CellRangeAddress(1,1,0,headerList.lastIndex))

        //創建項目標頭文字
        val headerRow = sheet.createNextRow()
        headerList.forEach {
            headerRow.createNextCell(it,headerStyle)
        }

        //項目清單創建
        dataList.forEachIndexed { index,data->
            val dataRow = sheet.createNextRow()

            dataRow.createNextCell(index + 1,dataStyle)
            dataRow.createNextCell(data.account,dataStyle)
            dataRow.createNextCell(data.name,dataStyle)
            dataRow.createNextCell(data.roleName,dataStyle)
            val statusStr = when(data.status){
                0 -> "停用"
                1 -> "啟用"
                else -> "未知"
            }
            dataRow.createNextCell(statusStr,dataStyle)
            dataRow.createNextCell(data.lastLoginTime,dataStyle)
        }

        headerList.forEachIndexed { index,_->
            //依內容自適應寬高
            sheet.autoSizeColumn(index)

            //依照自適應後的寬高做等比擴大
            sheet.getRow(index).height = (sheet.getRow(index).height *17 / 10).toShort()
            sheet.setColumnWidth(index,sheet.getColumnWidth(index) * 17/10)
        }
        return workbook
    }



}