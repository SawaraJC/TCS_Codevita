#include <iostream>
#include <vector>
#include <string>
#include <unordered_map>
#include <queue>
#include <tuple>
#include <climits>

using namespace std;

struct Flight {
    string destination;
    int departureTime;
    int arrivalTime;
    int price;
};

unordered_map<string, int> cityMap;
vector<vector<Flight>> graph;
int totalCities = 0;

int parseTime(const string &time) {
    int hours = stoi(time.substr(0, 2));
    int minutes = stoi(time.substr(3, 2));
    bool isPM = time.substr(5, 2) == "Pm";
    if (hours == 12) hours = 0;
    if (isPM) hours += 12;
    return hours * 60 + minutes;
}

int calculateMinimumCost(const string &start, const string &destination, int earliestTime, int latestTime) {
    vector<int> cost(totalCities, INT_MAX);
    vector<int> arrivalTime(totalCities, INT_MAX);
    auto compare = [](tuple<int, int, int> a, tuple<int, int, int> b) {
        return get<0>(a) > get<0>(b);
    };
    priority_queue<tuple<int, int, int>, vector<tuple<int, int, int>>, decltype(compare)> pq(compare);

    if (cityMap.find(start) == cityMap.end() || cityMap.find(destination) == cityMap.end())
        return -1;

    int startIdx = cityMap[start];
    int destinationIdx = cityMap[destination];

    cost[startIdx] = 0;
    arrivalTime[startIdx] = earliestTime;
    pq.push({0, startIdx, earliestTime});

    while (!pq.empty()) {
        auto [currentCost, currentCity, currentArrival] = pq.top();
        pq.pop();

        if (currentCity == destinationIdx) return currentCost;

        for (const Flight &flight : graph[currentCity]) {
            int nextCity = cityMap[flight.destination];
            if (flight.departureTime >= currentArrival && flight.departureTime >= earliestTime && flight.arrivalTime <= latestTime) {
                int newCost = currentCost + flight.price;
                if (newCost < cost[nextCity] || (newCost == cost[nextCity] && flight.arrivalTime < arrivalTime[nextCity])) {
                    cost[nextCity] = newCost;
                    arrivalTime[nextCity] = flight.arrivalTime;
                    pq.push({newCost, nextCity, flight.arrivalTime});
                }
            }
        }
    }

    return -1;
}

int main() {
    int flightCount;
    cin >> flightCount;

    graph.resize(50);

    for (int i = 0; i < flightCount; ++i) {
        string source, destination, depTime, arrTime;
        int price;
        cin >> source >> destination >> depTime >> arrTime >> price;

        int depMinutes = parseTime(depTime);
        int arrMinutes = parseTime(arrTime);

        if (cityMap.find(source) == cityMap.end()) {
            cityMap[source] = totalCities++;
        }
        if (cityMap.find(destination) == cityMap.end()) {
            cityMap[destination] = totalCities++;
        }

        graph[cityMap[source]].push_back({destination, depMinutes, arrMinutes, price});
    }

    string startCity, endCity, startPref, endPref;
    cin >> startCity >> endCity >> startPref >> endPref;

    int startTime = parseTime(startPref);
    int endTime = parseTime(endPref);

    int result = calculateMinimumCost(startCity, endCity, startTime, endTime);

    if (result == -1) {
        cout << "Impossible";
    } else {
        cout << result;
    }

    return 0;
}
