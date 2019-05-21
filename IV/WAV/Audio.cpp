#include "pch.h"
#include "Config.h"
#include "Audio.h"
#include <fstream>
using namespace Wav;

Audio::Audio() {
	audioBuffer = new short int[Wav::NumSamples]; // when this is filled idk what happens :D
	waveFormat = Wav::waveFormatTemplate;
	waveHeader = Wav::getWaveHeader((LPSTR)audioBuffer);
}


Audio::~Audio() {
	if (audioBuffer != NULL) {
		delete audioBuffer;
	}
}

void Audio::writeToFile(const char * filename) {
	std::ofstream wavFile(filename, std::ios::binary | std::ios::out);
	// @@@ RIFF chunk
	// ChunkID
	wavFile.write("RIFF", 4);
	// Chunk size
	unsigned int subchunk2size = ((waveHeader.dwBytesRecorded / 2) * waveFormat.nChannels * waveFormat.wBitsPerSample) / 8;
	unsigned int x = subchunk2size;
	wavFile.write((char*)&x, sizeof(x));
	// Format
	wavFile.write("WAVE", 4);


	// @@@ Fmt chunk
	wavFile.write("fmt ", 4);
	// fmt size
	x = 16;
	wavFile.write((char*)&x, sizeof(x));
	// audio format
	wavFile.write((char*)&waveFormat.wFormatTag, sizeof(waveFormat.wFormatTag));
	// num channels
	wavFile.write((char*)&waveFormat.nChannels, sizeof(waveFormat.nChannels));
	// sample rate
	wavFile.write((char*)&waveFormat.nSamplesPerSec, sizeof(waveFormat.nSamplesPerSec));
	// byte rate         
	wavFile.write((char*)&waveFormat.nAvgBytesPerSec, sizeof(waveFormat.nAvgBytesPerSec));
	// block align
	wavFile.write((char*)&waveFormat.nBlockAlign, sizeof(waveFormat.nBlockAlign));
	// bits per sample
	wavFile.write((char*)&waveFormat.wBitsPerSample, sizeof(waveFormat.wBitsPerSample));

	// @@@ DATA chunk
	wavFile.write("data", 4);
	// data size
	wavFile.write((char*)&subchunk2size, sizeof(subchunk2size));

	for (u_int i = 0; i < waveHeader.dwBytesRecorded; ++i) {
		wavFile.write((char*)&waveHeader.lpData[i], sizeof(waveHeader.lpData[i]));
	}

	wavFile.close();
}
