#include <iostream>
#include <vector>
#include <unordered_map>
#include <string>
#include <sstream>
#include <limits>
#include <algorithm>

using namespace std;

vector<string> breaks(const string &s, char seperator) {
    vector<string> pieces; 
    string token;
    stringstream s_stream(s);
    while (getline(s_stream, token, seperator)) {
        pieces.push_back(token);
    }
    return pieces;
}

int Orbs(const string &pot, unordered_map<string, vector<vector<string>>> &recipe, unordered_map<string, int> &demo) {
    if (recipe.find(pot) == recipe.end()) {
        return 0;
    }

    if (demo.find(pot) != demo.end()) {
        return demo[pot];
    }

    int minimum_Orbs = numeric_limits<int>::max(); 
    for (const auto &rec : recipe[pot]) {
        int orb = rec.size() - 1; 
        for (const string &ing_1 : rec) {
            orb += Orbs(ing_1, recipe, demo); 
        }
        minimum_Orbs = min(minimum_Orbs, orb);
    }

    demo[pot] = minimum_Orbs;
    return minimum_Orbs;
}

int min_orbs(int n, vector<string> &r, const string &tar_Pot) {
    unordered_map<string, vector<vector<string>>> recipes;
    unordered_map<string, int> demos;

    for (const string &recipe : r) {
        auto pieces = breaks(recipe, '=');
        string potion = pieces[0];
        auto ingred = breaks(pieces[1], '+'); 
        recipes[potion].push_back(ingred);
    }

    return Orbs(tar_Pot, recipes, demos);
}

int main() {
    int n;
    cin >> n;
    cin.ignore(); 

    vector<string> rec(n);
    for (int i = 0; i < n; ++i) {
        getline(cin, rec[i]); 
    }

    string tar_pot;
    getline(cin, tar_pot);

    cout << min_orbs(n, rec, tar_pot);

    return 0;
}
