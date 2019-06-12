#include "pch.h"
#include <Windows.h>
#include <MMSystem.h>
#include <iostream>
#include <fstream>
#include "Recorder.h"
using namespace Wav;


int main() {
	Audio audio;
	Recorder::startRecording(audio);
	std::cout << "ENTER to stop recording\n";
	getchar();
	Recorder::stopRecording(audio);
	audio.writeToFile("123.wav");
	return 0;
}
