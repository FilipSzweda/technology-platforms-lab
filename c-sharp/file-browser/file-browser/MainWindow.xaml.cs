using System.Windows;
using System.Windows.Controls;
using System.Windows.Forms;
using System.IO;

namespace file_browser {
    public partial class MainWindow : Window {
        public MainWindow() {
            InitializeComponent();
        }

        public TreeViewItem NewTreeViewItem(string path) {
            FileInfo file = new FileInfo(path);
            FileAttributes attr = File.GetAttributes(path);
            string header_1;
            var item = new TreeViewItem {
                Header = file.Name,
                Tag = path
            };
            item.ContextMenu = new System.Windows.Controls.ContextMenu();
            if ((attr & FileAttributes.Directory) == FileAttributes.Directory) {
                header_1 = "Create";
            }
            else {
                header_1 = "Open";
            }
            var menuItem_1 = new System.Windows.Controls.MenuItem { Header = header_1 };
            var menuItem_2 = new System.Windows.Controls.MenuItem { Header = "Delete" };
            item.ContextMenu.Items.Add(menuItem_1);
            item.ContextMenu.Items.Add(menuItem_2);
            menuItem_2.Click += new RoutedEventHandler(ContextDeleteClick);
            if (header_1 == "Open") {
                menuItem_1.Click += new RoutedEventHandler(ContextOpenClick);
            }
            if (header_1 == "Create") {
                menuItem_1.Click += new RoutedEventHandler(ContextCreateClick);
            }
            item.Selected += new RoutedEventHandler(StatusBarDOSAttributes);
            return item;
        }

        TreeViewItem PrepareDirectoryItem(DirectoryInfo directory) {
            var directoryItem = NewTreeViewItem(directory.FullName);
            FileInfo[] subFiles = directory.GetFiles();
            foreach (FileInfo subFile in subFiles) {
                var item = NewTreeViewItem(subFile.FullName);
                directoryItem.Items.Add(item);
            }
            DirectoryInfo[] subDirectories = directory.GetDirectories();
            foreach (DirectoryInfo subDirectory in subDirectories) {
                var item = PrepareDirectoryItem(subDirectory);
                directoryItem.Items.Add(item);
            }
            return directoryItem;
        }

        private void OpenClick(object sender, RoutedEventArgs e) {
            var folderBrowserDialog = new FolderBrowserDialog() { Description = "Select folder to open" };
            folderBrowserDialog.ShowDialog();
            var rootPath = folderBrowserDialog.SelectedPath;
            var rootDirectory = new DirectoryInfo(rootPath);
            var root = NewTreeViewItem(rootPath);
            FileInfo[] subFiles = rootDirectory.GetFiles();
            foreach (FileInfo subFile in subFiles) {
                var item = NewTreeViewItem(subFile.FullName);
                root.Items.Add(item);
            }
            DirectoryInfo[] subDirectories = rootDirectory.GetDirectories();
            foreach (DirectoryInfo subDirectory in subDirectories) {
                var item = PrepareDirectoryItem(subDirectory);
                root.Items.Add(item);
            }
            myTreeView.Items.Add(root);
        }

        private void ExitClick(object sender, RoutedEventArgs e) {
            Close();
        }

        private void DeleteDirectory(string path) {
            foreach (string directory in Directory.GetDirectories(path)) {
                DeleteDirectory(directory);
            }
            foreach (string file in Directory.GetFiles(path)) {
                File.Delete(file);
            }
            Directory.Delete(path);
        }

        private void ContextDeleteClick(object sender, System.EventArgs e) {
            TreeViewItem selectedItem = myTreeView.SelectedItem as TreeViewItem;
            string path = selectedItem.Tag as string;
            FileAttributes attributes = File.GetAttributes(path);
            File.SetAttributes(path, attributes & ~FileAttributes.ReadOnly);
            TreeViewItem parent = (TreeViewItem)selectedItem.Parent;
            parent.Items.Remove(selectedItem);
            if ((attributes & FileAttributes.Directory) != FileAttributes.Directory) {
                File.Delete(path);
            }
            else {
                DeleteDirectory(path);
            } 
        }

        private void StatusBarDOSAttributes(object sender, System.EventArgs e) {
            TreeViewItem item = myTreeView.SelectedItem as TreeViewItem;
            string file = item.Tag as string;
            string attributes = "";

            if ((File.GetAttributes(file) & FileAttributes.ReadOnly) == FileAttributes.ReadOnly) {
                attributes += "r";
            }  
            else {
                attributes += "-";
            }

            if ((File.GetAttributes(file) & FileAttributes.Archive) == FileAttributes.Archive) {
                attributes += "a";
            }  
            else {
                attributes += "-";
            }
                

            if ((File.GetAttributes(file) & FileAttributes.System) == FileAttributes.System) {
                attributes += "s";
            } 
            else {
                attributes += "-";
            }
                

            if ((File.GetAttributes(file) & FileAttributes.Hidden) == FileAttributes.Hidden) {
                attributes += "h";
            } 
            else {
                attributes += "-";
            }
                
            currentStatus.Text = attributes;
        }

        private void ContextOpenClick(object sender, System.EventArgs e) {
            TreeViewItem item = myTreeView.SelectedItem as TreeViewItem;
            string path = item.Tag as string;
            StreamReader stream = new StreamReader(path);
            scrollViewer.Content = new TextBlock() { Text = stream.ReadToEnd() };
        }

        private void ContextCreateClick(object sender, System.EventArgs e) {
            TreeViewItem folder = myTreeView.SelectedItem as TreeViewItem;
            string path = folder.Tag as string;
            CreateDialog createDialog = new CreateDialog(path);
            createDialog.ShowDialog();
            TreeViewItem newItem;
            if (createDialog.correct) {
                newItem = NewTreeViewItem(createDialog.path);
                folder.Items.Add(newItem);
            }
        }
    }
}
