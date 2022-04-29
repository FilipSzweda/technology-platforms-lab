using System.IO;

namespace files{
	public abstract class FileSystemInfoExt{
		public FileSystemInfo file;
		public int contentsNumber = 0;
		public string GetDOSAttributes(){
			string[] c = { "-", "r", "a", "h", "s" };
			return	c[1 * (file.Attributes.HasFlag(FileAttributes.ReadOnly) ? 1 : 0)] +
					c[2 * (file.Attributes.HasFlag(FileAttributes.Archive) ? 1 : 0)] +
					c[3 * (file.Attributes.HasFlag(FileAttributes.Hidden) ? 1 : 0)] +
					c[4 * (file.Attributes.HasFlag(FileAttributes.System) ? 1 : 0)];
		}

		protected internal abstract void Print(int depth);
		protected abstract string Format(int depth);

		public void Print() { Print(0); }
	}
}