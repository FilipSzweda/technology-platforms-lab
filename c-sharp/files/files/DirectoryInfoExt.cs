using System;
using System.Collections.Generic;
using System.IO;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;

namespace files{
	public class DirectoryInfoExt : FileSystemInfoExt{
		IList<FileSystemInfoExt> contents;

		public DirectoryInfoExt(DirectoryInfo directory){
			this.file = directory;
			this.contents = new List<FileSystemInfoExt>();
			foreach (FileSystemInfo content in directory.EnumerateFileSystemInfos()){
				if (content.GetType() == typeof(FileInfo)){
					this.contents.Add(new FileInfoExt(content as FileInfo));
				}
				else if (content.GetType() == typeof(DirectoryInfo)){
					this.contents.Add(new DirectoryInfoExt(content as DirectoryInfo));
				}
			}
			this.contentsNumber = contents.Count;
		}

		protected override string Format(int depth){
			string name = "";
			for (int i = 0; i < depth; i++){
				name += '\t';
			}
			name += String.Format("{0} ({1}) {2}", file.Name, ((DirectoryInfo)file).GetFileSystemInfos().Length, this.GetDOSAttributes());
			return name;
		}

		protected internal override void Print(int depth){
			Console.WriteLine(this.Format(depth));
			foreach (FileSystemInfoExt content in contents){
				content.Print(depth + 1);
			}
		}

		public static DateTime FindOldest(DirectoryInfo directory, DateTime oldest){
			foreach (var content in directory.EnumerateFileSystemInfos()){
				if (content.GetType() == typeof(DirectoryInfo)){
					DateTime tmp = FindOldest((DirectoryInfo)content, oldest);
					if (tmp < oldest){
						oldest = tmp;
					}
				}
				if (content.CreationTime < oldest){
					oldest = content.CreationTime;
				}
			}
			return oldest;
		}

		protected internal void PrintOldest(){
			DateTime oldest = FindOldest((DirectoryInfo) this.file, this.file.CreationTime);
			Console.WriteLine("\nNajstarszy plik: " + oldest + "\n");
		}

		public void CreateSortedAndSerialize(){
			SortedList<String, long> sorted = new SortedList<String, long>(new Comparer());
			foreach (var content in contents){
				sorted.Add(content.file.Name, content.contentsNumber);
			}
			FileStream fileStream = new FileStream("SerializedData.dat", FileMode.Create);
			BinaryFormatter binaryFormatter = new BinaryFormatter();
			try{
				binaryFormatter.Serialize(fileStream, sorted);
			}
			catch (SerializationException exception){
				Console.WriteLine("Error: " + exception.Message);
				throw;
			}
			finally{
				fileStream.Close();
			}
		}

		public void DeserializeAndPrint(){
			SortedList<String, long> sorted = new SortedList<String, long>(new Comparer());
			FileStream fileStream = new FileStream("SerializedData.dat", FileMode.Open);
			try{
				BinaryFormatter binaryFormatter = new BinaryFormatter();
				sorted = (SortedList<String, long>)binaryFormatter.Deserialize(fileStream);
			}
			catch (SerializationException exception){
				Console.WriteLine("Error: " + exception.Message);
				throw;
			}
			finally{
				fileStream.Close();
			}
			foreach (var data in sorted){
				Console.WriteLine("{0} -> {1}", data.Key, data.Value);
			}
		}
	}
}