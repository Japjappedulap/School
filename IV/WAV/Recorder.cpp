#include "pch.h"
#include "Recorder.h"
#include "Config.h"
#include "Audio.h"

namespace Wav {

	Recorder::Recorder() {}


	Recorder::~Recorder() {}


	void Recorder::startRecording(Audio &audio) {
		MMRESULT result = 0;

		// Open wave, this modifies inputHandle
		result = waveInOpen(&audio.inputHandle, WAVE_MAPPER, &audio.waveFormat, 0L, 0L, WAVE_FORMAT_DIRECT);

		if (result)
		{
			std::cout << "Fail step 1" << std::endl;
			std::cout << result << std::endl;
			Sleep(10000);
			return;
		}

		waveInPrepareHeader(audio.inputHandle, &audio.waveHeader, sizeof(WAVEHDR));

		// Insert a wave input buffer
		result = waveInAddBuffer(audio.inputHandle, &audio.waveHeader, sizeof(WAVEHDR));

		if (result)
		{
			std::cout << "Fail step 2" << std::endl;
			std::cout << result << std::endl;
			Sleep(10000);
			return;
		}

		// Start recording
		result = waveInStart(audio.inputHandle);

		if (result)
		{
			std::cout << "Fail step 3" << std::endl;
			std::cout << result << std::endl;
			Sleep(10000);
			return;
		}
	}


	void Recorder::stopRecording(Audio &audio) {
		waveInClose(audio.inputHandle);
	}
}
