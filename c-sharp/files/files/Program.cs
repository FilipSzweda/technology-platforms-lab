using System;
using System.IO;

namespace files{
    class Program{
        static void Main(string[] args){
            if (args.Length < 1){
                Console.Error.WriteLine("Error: no directory path given.");
                return;
            }

            string directoryPath = args[0];
            if (!Directory.Exists(directoryPath)){
                Console.Error.WriteLine("Error: directory path incorrect.");
                return;
            }

            DirectoryInfoExt directoryInfoExt = new DirectoryInfoExt(new DirectoryInfo(directoryPath));
            directoryInfoExt.Print();
            directoryInfoExt.PrintOldest();

            Console.WriteLine("Press enter to exit the program.");
            Console.ReadLine();
        }
    }
}
