using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Windows.Forms;
using System.IO;

namespace file_browser
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }

        TreeViewItem prepareDirectoryItem(DirectoryInfo directory)
        {
            var directoryName = directory.Name;
            var directoryPath = directory.FullName;
            var directoryItem = new TreeViewItem { Header = directoryName, Tag = directoryPath };
            FileInfo[] subFiles = directory.GetFiles();
            foreach (FileInfo subFile in subFiles)
            {
                var itemName = subFile.Name;
                var itemPath = subFile.FullName;
                var item = new TreeViewItem { Header = itemName, Tag = itemPath };
                directoryItem.Items.Add(item);
            }
            DirectoryInfo[] subDirectories = directory.GetDirectories();
            foreach (DirectoryInfo subDirectory in subDirectories)
            {
                var item = prepareDirectoryItem(subDirectory);
                directoryItem.Items.Add(item);
            }
            return directoryItem;
        }

        private void OpenClick(object sender, RoutedEventArgs e)
        {
            var folderBrowserDialog = new FolderBrowserDialog() { Description = "Select folder to open" };
            folderBrowserDialog.ShowDialog();
            var rootPath = folderBrowserDialog.SelectedPath;
            var rootDirectory = new DirectoryInfo(rootPath);
            var rootName = rootDirectory.Name;
            var root = new TreeViewItem { Header = rootName, Tag = rootPath };
            FileInfo[] subFiles = rootDirectory.GetFiles();
            foreach (FileInfo subFile in subFiles) {
                var itemName = subFile.Name;
                var itemPath = subFile.FullName;
                var item = new TreeViewItem { Header = itemName, Tag = itemPath };
                root.Items.Add(item);
            }
            DirectoryInfo[] subDirectories = rootDirectory.GetDirectories();
            foreach (DirectoryInfo subDirectory in subDirectories)
            {
                var item = prepareDirectoryItem(subDirectory); 
                root.Items.Add(item);
            }
            System.Windows.Controls.TreeView treeView = this.FindName("MyTreeView") as System.Windows.Controls.TreeView;
            treeView.Items.Add(root);
        }

        private void ExitClick(object sender, RoutedEventArgs e)
        {
            System.Windows.Application.Current.Shutdown();
        }
    }
}
