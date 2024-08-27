import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PDFSegmenter {

    static class TextBlock {
        float yPosition;
        String content;

        public TextBlock(float yPosition, String content) {
            this.yPosition = yPosition;
            this.content = content;
        }

        public float getYPosition() {
            return yPosition;
        }

        public String getContent() {
            return content;
        }
    }

    public static void main(String[] args) {
        try {
            String inputFilePath = "C:\\Users\\Stuti\\Desktop\\PDFSegmenterProject\\Stuti Khanduja.pdf"; 
            int X = 3; // Number of segments

            List<TextBlock> textBlocks = extractTextBlocks(inputFilePath);

            List<List<TextBlock>> sections = segmentTextBlocks(textBlocks, X);

            int sectionNum = 1;
            for (List<TextBlock> section : sections) {
                System.out.println("Section " + sectionNum + ":");
                for (TextBlock block : section) {
                    System.out.println(block.getContent());
                }
                sectionNum++;
                System.out.println("\n-----------------------\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<TextBlock> extractTextBlocks(String filePath) throws IOException {
        File file = new File("C:\\Users\\Stuti\\Desktop\\PDFSegmenterProject\\Stuti Khanduja.pdf");
        List<TextBlock> textBlocks = new ArrayList<>();

        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper stripper = new PDFTextStripper() {
                float previousY = -1;
                StringBuilder contentBuilder = new StringBuilder();

                @Override
                protected void writeString(String string, List<TextPosition> textPositions) throws IOException {
                    if (textPositions.size() > 0) {
                        float currentY = textPositions.get(0).getYDirAdj();

                        if (previousY != -1 && Math.abs(previousY - currentY) > 20) {
                            textBlocks.add(new TextBlock(previousY, contentBuilder.toString()));
                            contentBuilder.setLength(0);
                        }

                        contentBuilder.append(string).append(" ");
                        previousY = currentY;
                    }
                }

                @Override
                protected void endPage(PDDocument document, int page) throws IOException {
                    if (contentBuilder.length() > 0) {
                        textBlocks.add(new TextBlock(previousY, contentBuilder.toString()));
                        contentBuilder.setLength(0);
                    }
                }
            };

            stripper.getText(document);
        }

        return textBlocks;
    }

    public static List<List<TextBlock>> segmentTextBlocks(List<TextBlock> textBlocks, int X) {
        List<List<TextBlock>> sections = new ArrayList<>();

        Collections.sort(textBlocks, Comparator.comparing(TextBlock::getYPosition).reversed());

        int blocksPerSection = (int) Math.ceil((double) textBlocks.size() / X);

        for (int i = 0; i < textBlocks.size(); i += blocksPerSection) {
            int end = Math.min(i + blocksPerSection, textBlocks.size());
            sections.add(new ArrayList<>(textBlocks.subList(i, end)));
        }

        return sections;
    }
}
