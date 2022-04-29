using System;
using System.Collections.Generic;

namespace files{
    [Serializable]
    public class Comparer : IComparer<string>{
        public int Compare(string x, string y){
            int diff = x.Length.CompareTo(y.Length);
            if (diff != 0){
                return diff;
            }
            return x.CompareTo(y);
        }
    }
}