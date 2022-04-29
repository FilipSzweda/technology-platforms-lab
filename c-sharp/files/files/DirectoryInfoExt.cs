using System;
using System.Collections.Generic;
using System.IO;

public class DirectoryInfoExt : FileSystemInfoExt{
	IList<FileSystemInfoExt> contents;
	DirectoryInfo originalDirectory;

	public DirectoryInfoExt(DirectoryInfo directory){
		this.originalDirectory = directory;
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

	public static FileSystemInfo FindOldest(DirectoryInfo current, FileSystemInfo oldest){
		foreach (var content in current.EnumerateFileSystemInfos()){
			if (content.GetType() == typeof(DirectoryInfo)){
				FileSystemInfo tmp = FindOldest((DirectoryInfo)content, oldest);
				if (tmp.CreationTime > oldest.CreationTime){
					oldest = tmp;
				}
			}
			if (content.CreationTime > oldest.CreationTime){
				oldest = content;
			}
		}
		return oldest;
	}

	protected internal void PrintOldest(){
		FileSystemInfo oldest = this.originalDirectory;
		foreach (var content in this.originalDirectory.EnumerateFileSystemInfos()){
			if (content.GetType() == typeof(DirectoryInfo)){
				FileSystemInfo tmp = FindOldest((DirectoryInfo)content, oldest);
				if (tmp.CreationTime > oldest.CreationTime){
					oldest = tmp;
				}
			}
			if (content.CreationTime > oldest.CreationTime){
				oldest = content;
			}
		}
		Console.WriteLine("Najstarszy plik: " + oldest.Name);
	}

	public IDictionary<string, int> GetCollection(IComparer<string> cmp){
		SortedDictionary<string, int> sortedDictionary = new SortedDictionary<string, int>(cmp);
		foreach (var content in contents){
			if (content.GetType() == typeof(DirectoryInfo)){
				sortedDictionary.Add(content.File.Name, ((DirectoryInfo)content.File).GetFileSystemInfos().Length);

			}
			else if (content.GetType() == typeof(FileInfo)){
				sortedDictionary.Add(content.File.Name, (int)((FileInfo)content.File).Length);

			}
		}
		return sortedDictionary;
	}
}