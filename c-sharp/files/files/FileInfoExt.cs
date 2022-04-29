using System;
using System.IO;

namespace files{
	public class FileInfoExt : FileSystemInfoExt{
		public FileInfoExt(FileInfo file) { this.file = file; }

		protected override string Format(int depth){
			string name = "";
			for (int i = 0; i < depth; i++){
				name += '\t';
			}
			name += String.Format("{0} {1} bajtow {2}", file.Name, ((FileInfo)file).Length, this.GetDOSAttributes());
			return name;
		}

		protected internal override void Print(int depth){
			Console.WriteLine(this.Format(depth));
		}
	}
}