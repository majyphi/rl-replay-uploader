# RL-Replay-Uploader

RL-Replay-Uploader is a simple tool to automatically upload your Rocket League replays to [ballchasing.com](ballachasing.com) when you quit Rocket League.

It is essentially targeted at Linux users, as for Windows there is already [BakkesMod](https://bakkesmod.com/) with the [AutoReplayUploader](https://github.com/bakkesmodorg/AutoReplayUploader) plugin.

It uploads directly to ballchasing.com with the Rocket League built-in replay files, and keeps track of potential duplicates.


## Requirements

To install it, you will need to build the project with [maven](https://maven.apache.org/).

## Installation

- Clone or Download the project on your environment

- Package the project with :
```
mvn package
```
- Move the resulting JAR file in the ```rl-replay-uploader``` folder :
```
mv target/rl-replay-uploader-1.0-jar-with-dependencies.jar rl-replay-uploader/
```
- Fill in the properties needed in ```application.properties```:  
    - ```auth.key``` : Your upload token from ballchasing.com. Found [here](https://ballchasing.com/upload).  
    - ```replay.folder``` : Rocket League replays folder. Usually (on Linux) : ```/home/<USERNAME>/.local/share/Rocket League/TAGame/Demos/```  
    - ```uploaded.list``` : The file used to keep track of already uploaded replays. Can be kept at default.

- Copy the "replay-uploader" folder into your home directory (or any convenient directory) :
```
cp -r rl-replay-uploader ~/
```

- Add post-command to Rocket League Steam launch :  
(Right-click on Rocket League in Steam -> Properties -> "Set Launch Options...")  
```
%command% ; ${HOME}/rl-replay-uploader/run.sh
```

## Usage

Just run the game, save some replays, and come back to the desktop.

You can check the newly created ```replay-uploader.log```file to see if everything ran smoothly.

## Troubleshooting

#####Duplication error
If you see an error with this pattern : 
```
Error returned for [...].replay 
with following information :{"chat":{"Foamer":"Great Pass!","YOU":"All Yours."},"error":"duplicate replay","id":"[...]","location":"https://ballchasing.com/replay/[...]"}
```
It is a false negative. The match replay has already been uploaded in the past (by you or another person), the application has however tracked it to avoid this error in the future.

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Tests are indeed required, however as this piece of software is essentially I/O, a lot of mock will be needed.