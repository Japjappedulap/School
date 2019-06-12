#ifndef _CONFIG_GUARD
#define _CONFIG_GUARD

#include <Windows.h>
#include <MMSystem.h>

namespace Wav {
	const int sampleRate = 22050;
	const int NumSamples = 22050 * 30; // 30 seconds at max

	const WAVEFORMATEX waveFormatTemplate = {
		WAVE_FORMAT_PCM,	// wFormatTag
		2,					// nChannels;
		sampleRate,			// nSamplesPerSec;
		sampleRate * 4,		// nAvgBytesPerSec; nSamplesPerSec * n.Channels * wBitsPerSample/8
		4,					//nBlockAlign;
		16,					//wBitsPerSample;
		0,					//cbSize;
	};
	const WAVEHDR waveHeaderTemplate = {
		NULL,			// lpData;
		NumSamples * 2, // dwBufferLength;
		0,				// dwBytesRecorded;
		0L,				// dwUser;
		0L,				// dwFlags;
		0L,				// dwLoops;
	};

	static WAVEHDR getWaveHeader(LPSTR lpData) {
		WAVEHDR newWaveHeader = waveHeaderTemplate;
		newWaveHeader.lpData = lpData;
		return newWaveHeader;
	}
}

#endif
