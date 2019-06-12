#ifndef _RECORDER_GUARD
#define _RECORDER_GUARD

#include "pch.h"
#include "Audio.h"
#include <Windows.h>
#include <MMSystem.h>
#include <iostream>
#include <fstream>

namespace Wav {
	class Recorder {
	public:
		Recorder();
		~Recorder();
		static void startRecording(Audio &audio);
		static void stopRecording(Audio &audio);
	};
}

#endif