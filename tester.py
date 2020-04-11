import os

BIN_DIR = r'bin'
FILE_PATH = r'src/Main.java'

def tester():
    elements = 100
    threads = 100

    src_dir = os.path.dirname(FILE_PATH)
    compiled_class = os.path.basename(FILE_PATH).replace('.java', '')

    compile = f'javac -sourcepath {src_dir} -d {BIN_DIR} {FILE_PATH}'
    execute = f'java -classpath {BIN_DIR} {compiled_class} {elements} {threads}'
    os.system(compile)
    os.system(execute)


if __name__ == '__main__':
    tester()