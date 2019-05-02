const request = "https://www.speedrun.com/api/v1/leaderboards/{GAME}/category/{CATEGORY}";
handleData(process.argv[2], process.argv[3]);

function handleData(game, cat) {
    return new Promise((fulfill) => {
        let promises = [];
        let players = "";
        let category = cat.replace(/\+/g, "%2b");
        let url = request.replace("{GAME}", game).replace("{CATEGORY}", category);
        // console.log(game + category);
        // console.log(url);
        promises.push(getData(url).then((data) => {
            for (let run of data.data.runs) {
                promises.push(getData(run.run.players[0].uri).then((user) => {
                    let player = {};
                    if (typeof user.data.names === "undefined")
                        player["name"] = user.data.name;
                    else
                        player["name"] = user.data.names.international;
                    player["time"] = run.run.times.primary_t;
                    players += player["name"] + " " + player["time"] + "\n";
                }));
            }
            Promise.all(promises).then(() => {
                // for (let player of players) {
                //     console.log(JSON.stringify(player));
                // }
                writeToDisk(players, game, cat);
                fulfill(1);
            });
        }));
    });
}

function getData(url) {
    return new Promise((fulfill, reject) => {
        const https = require("follow-redirects").https;
        https.get(url, (res) => {
            res.setEncoding("utf8");
            let rawData = "";
            res.on("data", (chunk) => { rawData += chunk; });
            res.on("end", () => {
                try {
                    const parsedData = JSON.parse(rawData);
                    fulfill(parsedData);
                }
                catch (e) {
                    reject(e.message);
                }
            });
        }).on("error", () => {
            reject("Got error");
        });
    });
}

function writeToDisk(players, game , category) {
    const fs = require("fs-extra");
    fs.outputFile("./data/" + game + " " + category + ".txt", players)
        .catch(() => {
            console.error("Error: Failed to write file to disk");
        });
}