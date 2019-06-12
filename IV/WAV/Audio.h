#ifndef _AUDIO_GUARD
#define _AUDIO_GUARD

#include <Windows.h>
#include <MMSystem.h>


namespace Wav {
	class Audio {
	public:
		HWAVEIN inputHandle;
		WAVEFORMATEX waveFormat;
		WAVEHDR waveHeader;

		short int *audioBuffer = NULL;

		Audio();
		~Audio();
		void writeToFile(const char* filename);
	};
}

#endif