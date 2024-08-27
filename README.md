# PDFSegmenter

The PDF Segmenter is a Java application that programmatically segments a system-generated PDF into distinct sections based on the whitespace between blocks of text. The goal is to identify logical sections such as headings, paragraphs, and distinct blocks that are visually separated by increased whitespace, without using image processing techniques.

## Prerequisites

- **Java Development Kit (JDK)** - Ensure you have JDK 8 or higher installed.
- **Apache PDFBox** - Download and add the necessary JAR files to your project:
  - `pdfbox-x.x.x.jar`
  - `fontbox-x.x.x.jar`
  - `commons-logging-x.x.jar`

## Installation

1. Clone the repository or download the source code:
    ```bash
    git clone https://github.com/yourusername/pdf-segmenter.git
    ```
   
2. Navigate to the project directory:
    ```bash
    cd pdf-segmenter
    ```

3. Add the necessary PDFBox JAR files to your project's `lib` directory.

